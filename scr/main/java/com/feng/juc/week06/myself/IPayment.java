package com.feng.juc.week06.myself;

import java.math.BigDecimal;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/7/20 14:45
 * @Desc
 * @Since 1.0
 */
public interface IPayment {
    Boolean pay(String account, BigDecimal amount);
}
