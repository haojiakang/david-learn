package com.david.lsp;

/**
 * Created by fsdevops on 10/14/16.
 */
public class NullPointerExceptionTest {

    public static void main(String[] args) {
        String s = null;
        Object obj = s;
        String str = (String) obj;
        int length = str.length();
        System.out.println(length);
    }
}
