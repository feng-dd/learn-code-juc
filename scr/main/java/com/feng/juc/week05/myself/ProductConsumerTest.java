package com.feng.juc.week05.myself;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/6/30 15:41
 * @Desc 生产者消费者
 * @Since 1.0
 */
public class ProductConsumerTest {

    public static void main(String[] args) {

        // 阻塞队列
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

        // 生产者
        ExecutorService productThread = Executors.newFixedThreadPool(2);
        // 消费者
        ExecutorService customerThread = Executors.newFixedThreadPool(3);

        productThread.submit(() -> {
            System.out.println("生产消息");
            String msgBody = "消息";
            try {
                queue.put(msgBody);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        customerThread.submit(() -> {
            System.out.println("消费消息");
            try {
                String msg = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
