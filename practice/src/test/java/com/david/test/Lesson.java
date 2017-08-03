package com.david.test;

public class Lesson{
    public static void main(String [] args) {

        //二维数组的声明方式：
        //数据类型 [][] 数组名称 = new 数据类型 [长度][长度] ;
        //数据类型 [][] 数组名称 = {{123},{456}} ;

        /*
        int [][] arr = {{123},{456}};  //定义了两行三列的二维数组并赋值
        for(int x = 0; x<arr.length; x++){  //定位行
            for(int y = 0; y<arr[x].length; y++){  //定位每行的元素个数
                System.out.print(arr[x][y]);
            }
            System.out.println("/n");
        }
        */
        int [][] num = new int [3][2]; //定义了三行三列的二维数组
        num[0][0] = 1; //给第一行第一个元素赋值
        num[0][1] = 2; //给第一行第二个元素赋值
//        num[0][2] = 3; //给第一行第三个元素赋值

        num[1][0] = 4; //给第二行第一个元素赋值
        num[1][1] = 5; //给第二行第二个元素赋值
//        num[1][2] = 6; //给第二行第三个元素赋值

        num[2][0] = 7; //给第三行第一个元素赋值
        num[2][1] = 8; //给第三行第二个元素赋值
//        num[2][2] = 9; //给第三行第三个元素赋值
        for(int x = 0; x<num.length; x++){  //定位行
            for(int y = 0; y<num[x].length; y++){  //定位每行的元素个数
                System.out.print(num[x][y]);
            }
            System.out.println("/n");
        }
    }
}
