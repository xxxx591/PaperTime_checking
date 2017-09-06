package com.tocheck.parent.common.util;

//用二进制位来表示状态， option的二进制表示下，从右边数起，依次是第0位，第1位，第2位。
//第0位 标示用户是否可以申请结算
//第1位 检测费是否已经结算
//第2位 流量费是否已经结算
public class SettlementStatusUtils {


    public static int canApply(int option) {
        return option | 0x1;
    }

    public static boolean isCanApply(int option) {
        return (option & 0x1) > 0;
    }

    public static int addTargetFee(int option) {
        return option | 0x2;
    }

    public static boolean isTargetFee(int option) {
        return (option & 0x2) > 0;
    }

    public static int addFlowFee(int option) {
        return option | 0x4;
    }

    public static boolean isFlowFee(int option) {
        return (option & 0x4) > 0;
    }
}
