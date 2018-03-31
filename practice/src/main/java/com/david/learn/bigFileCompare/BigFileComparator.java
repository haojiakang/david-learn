package com.david.learn.bigFileCompare;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 大文件比较类
 * @author jiakang
 * Created by jiakang on 2018/03/31.
 */
@Slf4j
public class BigFileComparator {
    private static final String LINE_ENTER = System.getProperty("line.separator");

    private String baseFile;
    private String toCompareFile;
    private String resultFile;

    public BigFileComparator(String baseFile, String toCompareFile, String resultFile) {
        this.baseFile = baseFile;
        this.toCompareFile = toCompareFile;
        this.resultFile = resultFile;
    }

    /**
     * 比较并写入最终文件
     */
    public void compareAndWriteFile() {
        log.info("compareAndWriteFile started...");
        int taskNum = 2;
        CountDownLatch countDownLatch = new CountDownLatch(taskNum);
        ExecutorService executorService = Executors.newFixedThreadPool(taskNum);
        List<Future<String>> futures = new ArrayList<>();

        //提交2个归并排序task
        futures.add(executorService.submit(new MergeOrderTask(baseFile, countDownLatch)));
        log.info("baseFile task submit, baseFile:{}", baseFile);
        futures.add(executorService.submit(new MergeOrderTask(toCompareFile, countDownLatch)));
        log.info("toCompareFile task submit, toCompareFile:{}", baseFile);

        try {
            //等待2个task完成
            countDownLatch.await();
            String sortedBaseFile = futures.get(0).get();
            log.info("baseFile task done, sortedBaseFile:{}", sortedBaseFile);
            String sortedToCompareFile = futures.get(1).get();
            log.info("toCompareFile task done, sortedToCompareFile:{}", sortedToCompareFile);

            //比对排好序之后的2个大文件，并将想同行写入最终文件
            log.info("compareSortedAndWrite started, sortedBaseFile:{}, sortedToCompareFile:{}, resultFile:{}", sortedBaseFile, sortedToCompareFile, resultFile);
            compareSortedAndWrite(sortedBaseFile, sortedToCompareFile, resultFile);
            log.info("compareSortedAndWrite done, sortedBaseFile:{}, sortedToCompareFile:{}, resultFile:{}", sortedBaseFile, sortedToCompareFile, resultFile);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 比对排好序之后的2个大文件，并将想同行写入最终文件
     * @param sortedBaseFile
     * @param sortedToCompareFile
     * @param resultFile
     */
    private void compareSortedAndWrite(String sortedBaseFile, String sortedToCompareFile, String resultFile) {
        try (BufferedReader sortedBaseFileReader = new BufferedReader(new FileReader(new File(sortedBaseFile)));
             BufferedReader sortedToCompareFileReader = new BufferedReader(new FileReader(new File(sortedToCompareFile)));
             FileWriter fileWriter = new FileWriter(new File(resultFile))) {

            List<String> cacheLines = new ArrayList<>();
            String baseLine = null;
            boolean baseReadNext = true;

            String toCompareLine = null;
            boolean toCompareReadNext = true;

            while (true) {
                if (baseReadNext) {
                    baseLine = sortedBaseFileReader.readLine();
                }
                if (toCompareReadNext) {
                    toCompareLine = sortedToCompareFileReader.readLine();
                }
                if (baseLine == null || toCompareLine == null) {
                    break;
                }

                int baseLarger = baseLine.compareTo(toCompareLine);
                if (baseLarger > 0) {
                    toCompareReadNext = true;
                    baseReadNext = false;
                } else if (baseLarger == 0) {
                    toCompareReadNext = true;
                    baseReadNext = true;
                    cacheLines.add(baseLine);
                } else {
                    toCompareReadNext = false;
                    baseReadNext = true;
                }
                if (cacheLines.size() > 10000) {
                    writeToFile(fileWriter, cacheLines);
                    log.info("writeToFile, file:{}, cacheLines.size:{}", resultFile, cacheLines.size());
                    cacheLines.clear();
                }
            }

            if (!CollectionUtils.isEmpty(cacheLines)) {
                writeToFile(fileWriter, cacheLines);
                log.info("writeToFile, file:{}, cacheLines.size:{}", resultFile, cacheLines.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class MergeOrderTask implements Callable<String> {

        private String file;
        private CountDownLatch countDownLatch;

        public MergeOrderTask(String file, CountDownLatch countDownLatch) {
            this.file = file;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public String call() {
            String sortedFile = null;
            try (BufferedReader fileReader = new BufferedReader(new FileReader(new File(file)))) {
                List<String> subFiles = new ArrayList<>();
                List<String> bufferLines = new ArrayList<>();
                int subFileNum = 0;
                String line;
                while ((line = fileReader.readLine()) != null) {
                    bufferLines.add(line);
                    if (bufferLines.size() >= 10000) {
                        String subFileName = createExtFileName(file, "_sub" + subFileNum);
                        subFiles.add(subFileName);

                        //按10000行分割大文件，排序后写入小文件
                        Collections.sort(bufferLines);
                        writeToSubFile(bufferLines, subFileName);
                        log.info("writeToSubFile, subFileName:{}, bufferedLines.size:{}", subFileName, bufferLines.size());
                        bufferLines.clear();
                        subFileNum++;
                    }
                }

                //如果还有剩余的行数，排序后写入小文件
                if (!CollectionUtils.isEmpty(bufferLines)) {
                    String subFileName = createExtFileName(file, "_sub" + subFileNum);
                    subFiles.add(subFileName);

                    Collections.sort(bufferLines);
                    writeToSubFile(bufferLines, subFileName);
                    log.info("writeToSubFile, subFileName:{}, bufferedLines.size:{}", subFileName, bufferLines.size());
                }

                int size = 10;
                ExecutorService executorService = Executors.newFixedThreadPool(size);

                //使用线程池进行小文件的循环归并
                AtomicInteger sortLoopNum = new AtomicInteger(0);
                int splitSize = 100;
                while (subFiles.size() > splitSize) {
                    List<String> sortLoopFiles = Collections.synchronizedList(new ArrayList<>());
                    List<List<String>> partition = Lists.partition(subFiles, splitSize);

                    CountDownLatch interCountDownLatch = new CountDownLatch(partition.size());

                    for (List<String> curList : partition) {
                        executorService.submit(() -> {
                            String sortLoopFile = createExtFileName(file, "_sorted_loop" + sortLoopNum.getAndIncrement());
                            sortLoopFiles.add(sortLoopFile);
                            mergeSubFilesToSortedFile(curList, sortLoopFile);

                            removeInternalFiles(curList);
                            interCountDownLatch.countDown();
                        });
                    }
                    interCountDownLatch.await();

                    subFiles = sortLoopFiles;
                }

                //最后一次归并小文件写入最终的大文件
                sortedFile = createExtFileName(file, "_sorted");
                log.info("mergeSubFilesToSortedFile start, subFiles:{}, sortedFile:{}", subFiles, sortedFile);
                mergeSubFilesToSortedFile(subFiles, sortedFile);
                log.info("mergeSubFilesToSortedFile done, subFiles:{}, sortedFile:{}", subFiles, sortedFile);

                removeInternalFiles(subFiles);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            countDownLatch.countDown();
            return sortedFile;
        }

        private void removeInternalFiles(List<String> curList) {
            //将中间小文件删除
            for (String curFile : curList) {
                File file = new File(curFile);
                if (file.exists()) {
                    boolean result = file.delete();
                    log.info("curFile result, curFile:{}, result:{}", curFile, result);
                }
            }
        }

        /**
         * 将排序好的小文件按归并排序算法写入大文件
         * @param subFiles
         * @param sortedFile
         */
        private void mergeSubFilesToSortedFile(List<String> subFiles, String sortedFile) {
            List<BufferedReader> subFileReaders = new ArrayList<>(subFiles.size());
            for (String subFile : subFiles) {
                try {
                    subFileReaders.add(new BufferedReader(new FileReader(new File(subFile))));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            try (FileWriter fileWriter = new FileWriter(new File(sortedFile))) {
                List<String> cacheLines = new ArrayList<>();
                int size = subFileReaders.size();

                //key为第几个文件，value为当前行的值
                Map<Integer, String> cacheCursorMap = new HashMap<>(size);

                //读到末尾的文件数
                int iterEndFiles = 0;
                for (int i = 0; i < size; i++) {
                    iterEndFiles += updateCacheCursorMap(subFileReaders, cacheCursorMap, i);
                }

                //如果还有文件没读到末尾
                while (iterEndFiles < size) {
                    if (!CollectionUtils.isEmpty(cacheCursorMap)) {
                        //cacheCursorMap里value最小值对应的key
                        int minIndex = 0;
                        //cacheCursorMap里value最小值
                        String minLine = null;
                        for (int i = 0; i < size; i++) {
                            String curLine = cacheCursorMap.get(i);
                            if (curLine == null) {
                                continue;
                            }
                            if (minLine == null) {
                                minLine = curLine;
                                minIndex = i;
                            }

                            if (minLine.compareTo(curLine) > 0) {
                                minLine = curLine;
                                minIndex = i;
                            }
                        }

                        //更新cacheCursorMap里minIndex位置为文件的下一行，并计算iterEndFiles
                        iterEndFiles += updateCacheCursorMap(subFileReaders, cacheCursorMap, minIndex);
                        cacheLines.add(minLine);
                        if (cacheLines.size() >= 10000) {
                            //每10000行写入最终文件
                            writeToFile(fileWriter, cacheLines);
                            log.info("writeToFile, file:{}, cacheLines.size:{}", sortedFile, cacheLines.size());
                            cacheLines.clear();
                        }
                    }
                }

                //cacheLines如果还有数据，写入最终文件
                if (!CollectionUtils.isEmpty(cacheLines)) {
                    writeToFile(fileWriter, cacheLines);
                    log.info("writeToFile, file:{}, cacheLines.size:{}", sortedFile, cacheLines.size());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //关闭资源
            for (BufferedReader fileReader : subFileReaders) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            log.info("subFileReaders closed.");
        }

        /**
         * 更新cacheCursorMap的index位为下一行的值
         * @param subFileReaders
         * @param cacheCursorMap
         * @param index
         * @return 有多少个文件读到了末尾
         */
        private int updateCacheCursorMap(List<BufferedReader> subFileReaders, Map<Integer, String> cacheCursorMap, int index) {
            int endFileNum = 0;
            BufferedReader subFileReader = subFileReaders.get(index);
            try {
                String line;
                if ((line = subFileReader.readLine()) == null) {
                    endFileNum++;
                    cacheCursorMap.put(index, null);
                } else {
                    cacheCursorMap.put(index, line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return endFileNum;
        }

        private void writeToSubFile(List<String> bufferLines, String subFileName) {
            try (FileWriter fileWriter = new FileWriter(subFileName)) {
                String toWriteLines = String.join(LINE_ENTER, bufferLines) + LINE_ENTER;
                fileWriter.append(toWriteLines);
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String createExtFileName(String file, Object toInsertEnd) {
            int potIndex = file.indexOf(".");
            String pureFileName = file.substring(0, potIndex);
            String suffix = file.substring(potIndex);
            return pureFileName + toInsertEnd + suffix;
        }

    }

    private static void writeToFile(FileWriter fileWriter, List<String> bufferLines) {
        try {
            String toWriteLines = String.join(LINE_ENTER, bufferLines) + LINE_ENTER;
            fileWriter.append(toWriteLines);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String baseFile = "bigFile/source_big0.txt";
        String toCompareFile = "bigFile/source_big1.txt";
        String resultFile = "bigFile/result_same_big.txt";
        BigFileComparator bigFileComparator = new BigFileComparator(baseFile, toCompareFile, resultFile);

        bigFileComparator.compareAndWriteFile();
        log.info("bigFileComparator.compareAndWriteFile done. baseFile:{}, toCompareFile:{], resultFile:{}", baseFile, toCompareFile, resultFile);
        log.info("All done.");
    }
}
