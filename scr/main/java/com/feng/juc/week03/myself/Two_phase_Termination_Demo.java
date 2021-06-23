package com.feng.juc.week03.myself;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/6/21 16:15
 * @Desc 两阶段终止Demo
 *          1. 当满足某个条件((System.currentTimeMillis() - currentTime) >= mills)时，main线程 通知 executeThread 中断
 *          2.
 * @Since 1.0
 */
public class Two_phase_Termination_Demo {

    /**
     * 执行线程
     */
    private Thread executeThread;
    /**
     * 运行状态
     */
    private volatile boolean isRunning = false;

    /**
     *
     * @param task 发生阻塞的线程任务
     */
    public void execute(Runnable task) {
        executeThread = new Thread(() -> {
            Thread childThread = new Thread(task);

            // 子线程设置为守护线程
            childThread.setDaemon(true);
            childThread.start();
            try {
                // 强行执行子线程，使其进入休眠状态
                childThread.join();
                isRunning= true;
                System.out.println(isRunning);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                System.out.println("异常捕获");
            }
        });

        executeThread.start();
    }

    /**
     *
     * @param mills 强制结束任务的时长阈值
     */
    public void shutdown(long mills) {
        long currentTime = System.currentTimeMillis();
        while (!isRunning) {
            // 任务是否超过1s
            if ((System.currentTimeMillis() - currentTime) >= mills) {
                System.out.println("任务超时，需要结束他!");
                executeThread.interrupt();
                break;
            }


        }

        isRunning = false;
    }
    public static void main(String[] args) {

        Two_phase_Termination_Demo executor = new Two_phase_Termination_Demo();
        long start = System.currentTimeMillis();
        executor.execute(() -> {
            try {
                // 执行任务的时长2000ms
                Thread.sleep(2000);
                // 执行任务的时长500ms
//                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executor.shutdown(1000);
        System.out.println(executor.isRunning);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
