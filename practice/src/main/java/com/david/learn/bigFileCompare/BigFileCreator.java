package com.david.learn.bigFileCompare;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 大文件生成类
 * @author jiakang
 * Created by jiakang on 2018/03/31.
 */
@Slf4j
public class BigFileCreator {
    private static final String LINE_ENTER = System.getProperty("line.separator");

    private String workPath;
    private String destBigFile;
    private int lineNumber;

    public BigFileCreator(String workPath, String destBigFile, int lineNumber) {
        this.workPath = workPath;
        this.destBigFile = destBigFile;
        this.lineNumber = lineNumber;
    }

    private void createFile() {
        boolean result = checkOrMakeWorkPath();
        if (!result) {
            log.info("checkOrMakeWorkPath result false, so return");
            return;
        }

        makeRandomStrAndWriteFile();
    }

    private void makeRandomStrAndWriteFile() {
        FileWriter destWriter;
        try {
            if (!workPath.endsWith(File.separator)) {
                workPath = workPath + File.separator;
            }
            destWriter = new FileWriter(new File(workPath + destBigFile));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        List<String> cacheStrs = new ArrayList<>();
        for (int i = 0; i < lineNumber; i++) {
            String str = RandomUtil.createRandomStr();
            cacheStrs.add(str);
            if (cacheStrs.size() >= 10000) {
                flushStrsToFile(destWriter, cacheStrs);
                cacheStrs.clear();
            }
        }
        if (cacheStrs.size() > 0) {
            flushStrsToFile(destWriter, cacheStrs);
        }
        try {
            destWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void flushStrsToFile(FileWriter fileWriter, List<String> cacheStrs) {
        log.info("flushStrs to file start, fileWriter:{}, cacheStrs.size:{}", fileWriter, cacheStrs.size());
        String toWrite = String.join(LINE_ENTER, cacheStrs) + LINE_ENTER;
        try {
            fileWriter.append(toWrite);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("flushStrs to file end, fileWriter:{}, cacheStrs.size:{}", fileWriter, cacheStrs.size());
    }

    private boolean checkOrMakeWorkPath() {
        File workSpace = new File(workPath);
        if (!workSpace.exists() || workSpace.isFile()) {
            boolean result = workSpace.mkdirs();
            log.info("workSpace not exist or is file, so create. workPath:{}, result:{}", workPath, result);
            if (!result) {
                log.info("workSpace create dir failed, so return. workPath:{}", workPath);
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int lineNumber = 10000000;
        String workPath = "bigFile";

        String destBigFile = "source_big0.txt";
        BigFileCreator bigFileCreator = new BigFileCreator(workPath, destBigFile, lineNumber);
        bigFileCreator.createFile();
        log.info("big file create done. workPath:{}, destBigFile:{}, lineNumber:{}", workPath, destBigFile, lineNumber);

        String destBigFile1 = "source_big1.txt";
        BigFileCreator bigFileCreator1 = new BigFileCreator(workPath, destBigFile1, lineNumber);
        bigFileCreator1.createFile();
        log.info("big file create done. workPath:{}, destBigFile:{}, lineNumber:{}", workPath, destBigFile1, lineNumber);
    }
}
