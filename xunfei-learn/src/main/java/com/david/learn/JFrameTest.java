package com.david.learn;

import javax.swing.*;
import java.net.URL;

/**
 * Created by fsdevops on 10/18/16.
 */
public class JFrameTest {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test Window");
        String path = "res/index_bg.png";
        URL url = JFrameTest.class.getResource(path);
        url = JFrameTest.class.getClassLoader().getResource(path);
        ImageIcon background = new ImageIcon(url);
        //设置窗口大小，位置
        frame.setBounds(30, 30, 250, 200);
        //将窗口显示出来（Frame对象默认处于隐藏状态）
        frame.setVisible(true);
    }

}
