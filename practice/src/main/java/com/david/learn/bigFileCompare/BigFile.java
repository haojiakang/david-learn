package com.david.learn.bigFileCompare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BigFile {
    private List<String> subFileNameList = new ArrayList<String>();
    private String bigFilePath = "/Users/jiakang/bigfile/";

    private String bigFileName = "Source_yiwan.txt";
    private FileGenerator fGenerator = new FileGenerator();

    private static final int theMaxNumber = 10000000;
    private List<Integer> theContentWritingToSubFile = new ArrayList<Integer>(theMaxNumber);

    public void sortTheBigfile(String destination){
        splitTheBigFile();
        SourceFolder sFolder = new SourceFolder();
        sFolder.setFilenameList(subFileNameList);
        sFolder.setDestination(destination);
        try {
            sFolder.compareFiles();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        removeTheSubFiles();
    }

    public void setBigFilePath(String bigFilePath) {
        this.bigFilePath = bigFilePath;
    }

    public void setBigFileName(String bigFileName) {
        this.bigFileName = bigFileName;
    }

    private void removeTheSubFiles() {
        for(String fileName:subFileNameList){
            File file = new File(fileName);
            if(file.exists()&&file.canWrite()){
                System.out.println(fileName+":"+file.delete());;
            }
        }
    }

    public void splitTheBigFile(){
        System.out.println("begin to spliting the big files...");
        int counter = 0;
        File file = new File(bigFilePath+bigFileName);
        BufferedReader reader = null;
        FileReader fReader;
        int fileFlag = 1;
        try {
            fReader = new FileReader(file);
            reader = new BufferedReader(fReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                if(tempString.trim().equals("")){
                    break;
                }
                int tempInt = Integer.valueOf(tempString);
                theContentWritingToSubFile.add(tempInt);
                if(isTheListFull(counter)){
                    handleTheFullList(fileFlag);
                    theContentWritingToSubFile.clear();
                    fileFlag ++;
                }
            }
            handleTheFullList(fileFlag);
            fReader.close();
            reader.close();
            System.out.println("finishing the spliting work.");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void handleTheFullList(int fileFlag) throws Exception {
        System.out.println("handle the full list...");
        String tempFilePath = bigFilePath + fileFlag + ".txt";
        writeTheContentToSubFile(tempFilePath);
        subFileNameList.add(tempFilePath);
        theContentWritingToSubFile.clear();
        System.out.println("the full list is clear now...");
    }

    private void writeTheContentToSubFile(String tempFilePath) throws Exception {
        System.out.println("begin to write the content to sub file...");
        System.out.println("sub file path:"+tempFilePath);
        Collections.sort(theContentWritingToSubFile);
        fGenerator.setFileName(tempFilePath);
        fGenerator.setTheListNeedToWriteTofile(theContentWritingToSubFile);
        fGenerator.writeToFileFromTheList(true);
        System.out.println("finishing this loop of writing the content to sub file...");
    }

    private boolean isTheListFull(int counter) {
        return theContentWritingToSubFile.size() >= theMaxNumber;
    }

    public static void main(String[] args) {
        BigFile bf = new BigFile();
        bf.setBigFileName("Source_yiwan.txt");
        bf.sortTheBigfile("/Users/jiakang/bigfile/Source_yiwan_sorted.txt");
    }
}