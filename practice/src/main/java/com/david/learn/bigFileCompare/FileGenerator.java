package com.david.learn.bigFileCompare;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileGenerator {
    private int wholeLineNumber = 200000000;

    private List<Integer> theListNeedToWriteTofile;
    private String fileName;
    private FileWriter fWriter = null;

    //  public void write
    public static void main(String[] args) {
        String fileName = "/Users/jiakang/bigfile/Source_liangwan.txt";
        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.setFileName(fileName);
        try {
            fileGenerator.createRandomFile();
            fileGenerator.closeFileWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setWholeLineNumber(int wholeLineNumber) {
        this.wholeLineNumber = wholeLineNumber;
    }

    public void setTheListNeedToWriteTofile(List<Integer> theListNeedToWriteTofile) {
        this.theListNeedToWriteTofile = theListNeedToWriteTofile;
    }
    public void createRandomFile() throws Exception {
        int listSize = 10000000;
        theListNeedToWriteTofile = new ArrayList<Integer>(listSize);
        for(int i = 0; i < wholeLineNumber; i ++){
            int tempRandomInt = (int)(Math.random()*100000000);
            theListNeedToWriteTofile.add(tempRandomInt);
            if(theListNeedToWriteTofile.size()==listSize){
                System.out.println("begin to write to the file...");
                writeToFileFromTheList(false);
                theListNeedToWriteTofile.clear();
                System.out.println("finish this loop...");
            }
        }
        writeToFileFromTheList(false);
    }

    public void writeToFileFromTheList(boolean isNeedToCreateNewFile) throws Exception {
        System.out.println("write the content to file...");
        try {
            if(isNeedToCreateNewFile){
                createNewFile(fileName);
            }
            StringBuilder contentWritingToFile = new StringBuilder();
            int cycleLineNumber = 1000000;
            int counter = 0;
            fWriter = new FileWriter(fileName,true);
            for(int index = 0; index < theListNeedToWriteTofile.size(); index ++){
                int tempRandomInt = theListNeedToWriteTofile.get(index);
                contentWritingToFile.append(tempRandomInt+"\n");
                if(counter == cycleLineNumber){
                    fWriter.append(contentWritingToFile.toString());
                    counter = 0;
                    contentWritingToFile = new StringBuilder();
                }
            }
//          while(theListNeedToWriteTofile.size() != 0){
//              int tempRandomInt = theListNeedToWriteTofile.remove(0);
//              contentWritingToFile.append(tempRandomInt+"\n");
//              if(counter == cycleLineNumber){
//                  fWriter.append(contentWritingToFile.toString());
//                  counter = 0;
//                  contentWritingToFile = new StringBuilder();
//              }
//          }
            fWriter.append(contentWritingToFile.toString());
            System.out.println("done..........");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            closeFileWriter();
        }
    }

    private void closeFileWriter() throws IOException{
        if(fWriter != null){
            fWriter.close();
        }
    }
    private void createNewFile(String fileName) throws IOException {
        File file = new File(fileName);
        if(file.delete()){
            file.createNewFile();
        }
    }
}
