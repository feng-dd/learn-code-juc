package com.feng.juc.week03;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 *      线程终止功能接口
 **/
public interface Termination {

    /**
     * 请求终止线程
     */
    void terminate();
}
