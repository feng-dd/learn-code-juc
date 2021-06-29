package com.feng.juc.week04.myself;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/6/29 21:15
 * @Desc
 * @Since 1.0
 */
public class Promisor {

    public Future<Object> create(long startTime) {

        FutureTask<Object> promise = new FutureTask<Object>(() -> {
            System.out.println("开启烧水，用时：" + (System.currentTimeMillis() - startTime) + "ms");
            Thread.sleep(15000);
            return null;
        });
        Thread executor = new Thread(promise, "烧水线程");
        executor.start();
        return promise;
    }

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        // 烧水线程
        Future<Object> promise = new Promisor().create(startTime);
        try {
            // 洗茶杯
            Thread.sleep(3000);
            System.out.println("洗茶杯，用时3s");
            // 备茶叶
            Thread.sleep(1000);
            System.out.println("备茶叶，用时1s");
            // 查看水是否烧开
            if (!promise.isDone()) {
                System.out.println("水没有开，请等待");
            }
            Object o = promise.get();
            System.out.println("水烧开了，听声音：" + o);
            Thread.sleep(5000);
            System.out.println("泡茶叶，用时5s");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
