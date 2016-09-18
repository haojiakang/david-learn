package com.david.lsp;

/**
 * Created by Jackie on 2016/7/23
 * VM args: -Xss128k
 */
public class JavaVMStackSOF {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable throwable) {
            System.out.println("stack length: " + oom.stackLength);
            throw throwable;
        }
    }

}
