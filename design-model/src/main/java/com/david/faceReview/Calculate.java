package com.david.faceReview;

import java.io.*;

/**
 * Created by haojk on 3/29/17.
 */
public class Calculate {

    public static void main(String[] args) {
        Calculate calculate = new Calculate();
        String filePath = "/home/fsdevops/workspace/david-learn/design-model/src/main/java/com/david/";
        File file = new File(filePath);
        CountLine countLine = new CountLine();
        calculate.calculateFile(countLine, file);
        System.out.println(countLine);
//        System.out.println("/**".equals("/**"));
    }

    private static class CountLine {

        private int codeNum;
        private int commentNum;
        private int spaceNum;

        public int getCodeNum() {
            return codeNum;
        }

        public void setCodeNum(int codeNum) {
            this.codeNum = codeNum;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getSpaceNum() {
            return spaceNum;
        }

        public void setSpaceNum(int spaceNum) {
            this.spaceNum = spaceNum;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("CountLine{");
            sb.append("codeNum=").append(codeNum);
            sb.append(", commentNum=").append(commentNum);
            sb.append(", spaceNum=").append(spaceNum);
            sb.append('}');
            return sb.toString();
        }
    }

    public void calculateFile(CountLine countLine, File file) {
        if (!file.exists() || file.isFile()) {
            System.out.println("文件夹路径不存在或者路径不是文件夹,无法计算!");
            return;
        }
        File[] files = file.listFiles();
        if (files == null) {
            System.out.println("没有子文件");
            return;
        }
        for (File currentFile : files) {
            if (currentFile.isFile()) {
                CountLine currentCountLine = calculateNum(currentFile);
                combineCountLine(countLine, currentCountLine);
            } else {
                calculateFile(countLine, currentFile);
            }
        }

    }

    private void combineCountLine(CountLine countLine, CountLine currentCountLine) {
        countLine.setSpaceNum(countLine.getSpaceNum() + currentCountLine.getSpaceNum());
        countLine.setCommentNum(countLine.getCommentNum() + currentCountLine.getCommentNum());
        countLine.setCodeNum(countLine.getCodeNum() + currentCountLine.getCodeNum());
    }

    //
    /*


     */

    /**
     * kjkjklk
     * @param currentFile
     * @return
     */
    private CountLine calculateNum(File currentFile) {
        int codeNum = 0;  //asdfek
        int commentNum = 0/*  */;
        int spaceNum = 0;
        boolean inComment = false;
        try (
                BufferedReader br = new BufferedReader(new FileReader(currentFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String trimLine = line.trim();
                if ("".equals(trimLine)) {
                    if (inComment) {
                        commentNum ++;
                    } else {
                        spaceNum++;
                    }
                } else if (trimLine.startsWith("//") || trimLine.startsWith("/**") || (trimLine.startsWith("*") && !trimLine.startsWith("*/"))) {
                    commentNum++;
                } else if (trimLine.contains("//")) {
                    codeNum ++;
                    commentNum ++;
                } else if (trimLine.startsWith("/*")) {
                    commentNum ++;
                    inComment = true;
                } else if (trimLine.startsWith("*/")) {
                    commentNum ++;
                    inComment = false;
                } else {
                    if (inComment) {
                        commentNum ++;
                    } else {
                        codeNum ++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CountLine countLine = new CountLine();
        countLine.setCodeNum(codeNum);
        countLine.setCommentNum(commentNum);
        countLine.setSpaceNum(spaceNum);
        return countLine;
    }


}
