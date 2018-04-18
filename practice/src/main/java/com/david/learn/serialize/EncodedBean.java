package com.david.learn.serialize;

import java.io.*;

public class EncodedBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String password = "pass";

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private void writeObject(ObjectOutputStream out) {
        try {
            ObjectOutputStream.PutField putFields = out.putFields();
            System.out.println("原密码：" + password);
            password = "encryption"; //模拟加密
            putFields.put("password", password);
            System.out.println("加密后的密码：" + password);
            out.writeFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readObject(ObjectInputStream in) {
        try {
            ObjectInputStream.GetField readFields = in.readFields();
            Object object = readFields.get("password", "");
            System.out.println("要解密的字符串：" + object.toString());
            password = "pass"; //模拟解密，需要获得本地的密钥
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("result.obj"));
            out.writeObject(new EncodedBean());
            out.close();

            ObjectInputStream oin = new ObjectInputStream(new FileInputStream("result.obj"));
            EncodedBean encodedBean = (EncodedBean) oin.readObject();
            System.out.println("解密后的字符串：" + encodedBean.getPassword());
            oin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
