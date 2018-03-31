package com.david.learn.bigFileCompare;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceFolder {
    private List<String> filenameList;
    private Map<Integer, Integer> numberCache;
    private List<BufferedReader> bufferedReaderList;
    private int endFlagNumber = 0;
    private List<Integer> contentListWritingToFile;
    private FileGenerator fileGenerator;
    private String destination = "D:/bigfile/AllSource.txt";


    public SourceFolder() {
        contentListWritingToFile = new ArrayList<Integer>();
        filenameList = new ArrayList<String>();
        bufferedReaderList = new ArrayList<BufferedReader>();
        fileGenerator = new FileGenerator();
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    public void addFile(String filename) {
        this.filenameList.add(filename);
    }
    public void setFilenameList(List<String> filenameList) {
        this.filenameList = filenameList;
    }

    public void compareFiles() throws Exception {
        System.out.println("filenameList:"+filenameList);
        initTheBufferedReaderList();
        initTheNumberCache();
        while(!isAllFilesFinishTheComparing()){
            int theIndexOfReaderNeedingToMove = findTheLastIndexOfTheMinNumber();
            addTheNumberToFile(theIndexOfReaderNeedingToMove);
            updateNumberCache(theIndexOfReaderNeedingToMove);
        }
        addTheLastListToFile();
        closeAllIOStreams();
    }

    private void closeAllIOStreams() throws IOException {

        for(BufferedReader bReader:bufferedReaderList){
            if(bReader != null){
                bReader.close();
            }
        }

    }

    private int findTheLastIndexOfTheMinNumber() {
        int theIndexOfTheMinNumber = 0;
        int mixNumber = getTheFirstNumberFromTheCache();
        for (int index = 0; index < numberCache.size(); index++) {
            if(numberCache.get(index) == null){
                continue;
            }
            int theNumberWillBeCompared = numberCache.get(index);
            if (mixNumber >= theNumberWillBeCompared) {
                mixNumber = theNumberWillBeCompared;
                theIndexOfTheMinNumber = index;
            }
        }
        return theIndexOfTheMinNumber;
    }

    private int getTheFirstNumberFromTheCache() {
        for (int index = 0; index < numberCache.size(); index++) {
            if(numberCache.get(index) == null){
                continue;
            }
            return numberCache.get(index);
        }
        return 0;
    }

    private void addTheNumberToFile(int theIndexOfReaderNeedingToMove) throws Exception {
        contentListWritingToFile.add(numberCache.get(theIndexOfReaderNeedingToMove));
        if(contentListWritingToFile.size() == 1000000){
            fileGenerator.setTheListNeedToWriteTofile(contentListWritingToFile);
            fileGenerator.setFileName(destination);
            fileGenerator.writeToFileFromTheList( false);
            contentListWritingToFile.clear();
        }
    }

    private void updateNumberCache(int index) throws Exception {
        BufferedReader bufferedReader = bufferedReaderList.get(index);
        String tempString = null;
        if ((tempString = bufferedReader.readLine()) != null) {
            if (!"".equals(tempString.trim())) {
                int tempInt = Integer.valueOf(tempString);
                numberCache.put(index, tempInt);
            }
        } else {
            numberCache.put(index, null);
            endFlagNumber ++;
        }
    }

    private void addTheLastListToFile() throws Exception {
        fileGenerator.setTheListNeedToWriteTofile(contentListWritingToFile);
        fileGenerator.setFileName(destination);
        fileGenerator.writeToFileFromTheList(false);
    }



    private void initTheBufferedReaderList() throws FileNotFoundException {
        System.out.println("begin to initial the buffered reader...");
        for (String filename : filenameList) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(
                    filename));
            bufferedReaderList.add(bufferedReader);
        }
        System.out.println("finish initialing the buffered reader...");
    }

    private void initTheNumberCache() throws Exception {
        System.out.println("begin to initial the number cache...");
        numberCache = new HashMap<Integer, Integer>(filenameList.size());
        for (int index = 0; index < filenameList.size(); index++) {
            updateNumberCache(index);
        }
        System.out.println("finish initialing the number cache...");
    }

    private boolean isAllFilesFinishTheComparing() {

        return endFlagNumber == filenameList.size();
    }
}
