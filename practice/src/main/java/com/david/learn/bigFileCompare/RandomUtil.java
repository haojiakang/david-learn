package com.david.learn.bigFileCompare;

public class RandomUtil {
    //定义一个字符串（A-Z，a-z，0-9）即62位；
    private static final String STR_SEED = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
    private static final int SEED_LEN = STR_SEED.length();

    public static String createRandomStr() {
        int length = getRandomInt(2, 10);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = getRandomInt(SEED_LEN);
            char c = STR_SEED.charAt(index);
            sb.append(c);
        }
        return sb.toString();
    }

    private static int getRandomInt(int max) {
        return (int) (Math.random() * max);
    }

    private static int getRandomInt(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            String randomStr = createRandomStr();
            System.out.println(randomStr);
        }
    }
}
