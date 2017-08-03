package com.david.learn;

import org.junit.Test;

/**
 * Created by jiakang on 2017/6/17.
 */
public class InvestmentCalculate {

    private static final int DAY_OF_YEAR = 365;
    private static double cash = 40000.0;
    private static int day = 30;
    private static double rate_fund = 5.0510 * 0.01;
    private static double rate_yuebao = 4.1420 * 0.01;

    private double fund(double cash, int day) {
        double result = cash;
        for (int i = 0; i < day; i++) {
            result += cash * rate_fund / DAY_OF_YEAR;
        }
        return result;
    }

    private double yuebao(double cash, int day) {
        double result = cash;
        for (int i = 0; i < day; i++) {
            result += result * rate_yuebao / DAY_OF_YEAR;
        }
        return result;
    }

    private double calculateMinus(double cash, int day) {
        double result_fund = fund(cash, day);
        double result_yuebao = yuebao(cash, day);
        return result_fund - result_yuebao;
    }

    private int findLinjiedian(double cash) {
        int result = 0;
        for (int i = 0; ; i++) {
            double minus = calculateMinus(cash, i);
            if (minus < 0) {
                result = i;
                break;
            }
        }
        return result;
    }

    private int findzuiyouDay(double cash) {
        double preMinus = 0.0;
        int zuiyouDay;
        for (int i = 0; ; i++) {
            double minus = calculateMinus(cash, i);
            if (minus - preMinus < 0) {
                zuiyouDay = i;
                break;
            }
            preMinus = minus;
        }
        return zuiyouDay;
    }

    @Test
    public void test() {
        double result_fund = fund(cash, day);
        double result_yuebao = yuebao(cash, day);
        double minus = result_fund - result_yuebao;
        String hua;
        if (minus > 0) {
            hua = "基金的收益比较高，比余额宝多" + minus + "元";
        } else if (minus == 0) {
            hua = "基金的收益和余额宝持平";
        } else {
            hua = "余额宝的收益比较高，比基金多" + (0 - minus) + "元";
        }
        int linjiedian = findLinjiedian(cash);
        int zuiyouDay = findzuiyouDay(cash);
        double zuiyoujijin = fund(cash, zuiyouDay);
        double zuiyouyuebao = yuebao(cash, zuiyouDay);
        double zuiyouminus = zuiyoujijin - zuiyouyuebao;
        System.out.println("您一共有" + cash + "元的本金");
        System.out.println("基金的7日年化收益率为" + rate_fund / 0.01 + "，余额宝的7日年化收益率为" + rate_yuebao / 0.01);
        System.out.println("如果您的理财周期为" + day + "天，那么");
        System.out.println("存基金的话，预期本金为" + result_fund + "元；存余额宝的话，预期本金为" + result_yuebao + "元。");
        System.out.println(hua);
        System.out.println("其实，两种理财方式的收益多少临界点是第" + linjiedian + "天。少于" + linjiedian + "天时，基金收益较多；多于" + linjiedian + "天时，支付宝收益较多。");
        System.out.println("投资基金的最佳时间为" + zuiyouDay + "天，这天两种投资方式的本金差值最大。投资基金本金为" + zuiyoujijin + "元，投资余额宝本金为" + zuiyouyuebao + "元，差值为" + zuiyouminus + "元");
    }
}
