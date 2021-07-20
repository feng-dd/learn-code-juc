package com.feng.juc.week06.myself;

import java.math.BigDecimal;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/7/20 14:47
 * @Desc servant：支付宝付款
 * @Since 1.0
 */
public class ZfbPayment implements IPayment {

    @Override
    public Boolean pay(String account, BigDecimal amount) {
        System.out.println("支付宝付款");
        return true;
    }
}
