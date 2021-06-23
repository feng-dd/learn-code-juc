package com.feng.juc.week03.myself;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/6/21 16:51
 * @Desc
 *      用户线程：创建的普通线程
 *      守护线程：用来服务于用户线程；不需要上层逻辑介入，也可以手动创建一个守护线程
 * @Since 1.0
 */
public class DaemonThreadDemo {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Thread childThread = new Thread("childThread") {
            @Override
            public void run() {
                makeThreadSleep(10 * 1000);
                super.run();
                System.out.println("子线程运行耗时：" + (System.currentTimeMillis() - startTime));
            }
        };
        // 在调用start()方法前，调用setDaemon(true)把该线程标记为守护线程,否则会抛出IllegalThreadStateException异常。
        // 在Daemon线程中产生的新线程也是Daemon的
        // isDaemon()检查一个线程是守护线程还是用户线程
        childThread.setDaemon(true);
        childThread.start();
        //主线程暂定3秒，确保子线程都启动完成
        makeThreadSleep(3 * 1000);
        System.out.println("主线程运行耗时：" + (System.currentTimeMillis() - startTime));
    }

    // 使线程休眠一段时间
    private static void makeThreadSleep(long millSeconds) {
        try {
            Thread.sleep(millSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
