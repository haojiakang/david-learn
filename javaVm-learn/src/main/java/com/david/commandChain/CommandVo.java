package com.david.commandChain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by haojk on 1/25/17.
 */
public class CommandVo {

    //定义参数名与参数的分隔符号,一般是空格
    public final static String DIVIDE_FLAG = " ";

    //定义参数前的符号,UNIX一般是-, 如ls -la
    public final static String PREFIX = "-";

    //命令名,如ls, du
    private String commandName = "";

    //参数列表
    private List<String> paramList = new ArrayList<>();

    //操作数列表
    private List<String> dataList = new ArrayList<>();

    //通过构造函数传递进来的命令
    public CommandVo(String commandStr) {
        //常规判断
        if (commandStr != null && commandStr.length() != 0) {
            //根据分隔符号拆出执行符号
            String[] complexStr = commandStr.split(CommandVo.DIVIDE_FLAG);
            //第一个参数是执行符号
            this.commandName = complexStr[0];
            //把参数放到List中
            for (String command : complexStr) {
                //包含前缀符号,认为是参数
                if (command.indexOf(CommandVo.PREFIX) == 0) {
                    this.paramList.add(command.replace(CommandVo.PREFIX, "").trim());
                } else {
                    this.dataList.add(command.trim());
                }
            }
        } else {
            //传递的命令错误
            System.out.println("命令解析失败,必须传递一个命令才能执行!");
        }
    }

    public String getCommandName() {
        return commandName;
    }

    public List<String> getParam() {
        //为了方便处理空参数
        if (this.paramList.size() == 0) {
            this.paramList.add("");
        }
        return new ArrayList<>(new HashSet<>((this.paramList)));
    }

    public List<String> getData() {
        return dataList;
    }

    public String formatData() {
        StringBuilder sb = new StringBuilder();
        for (String str : dataList) {
            sb.append(str);
            sb.append(" ");
        }
        return sb.substring(0, sb.lastIndexOf(" "));
    }
}