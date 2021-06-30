package com.feng.juc.week02.myself;

import org.omg.CORBA.Request;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/6/16 10:04
 * @Desc
 *      用于处理来自多个客户端的请求,
 *      而维护的一个缓冲区!
 *      1.客户端请求存储到缓冲区
 *      2.服务器从缓存区获取请求执行
 *      3.缓冲区没有请求，服务器等待
 *      4.缓存区收到请求，通知服务器获取请求执行
 *
 * @Since 1.0
 */
public class RequestQueue {

    private final Integer MAX_LIMIT;

    private final Queue<Request> queue;

    private final ReentrantLock lock;

    private final Condition condition;

    public RequestQueue(Integer maxLimit) {
        this(maxLimit, new ReentrantLock());
    }

    public RequestQueue(Integer maxLimit, ReentrantLock lock) {
        this.MAX_LIMIT = maxLimit;
        this.queue = new ArrayBlockingQueue<>(MAX_LIMIT);
        this.lock = lock;
        this.condition = lock.newCondition();
    }

    public RequestQueue(Integer maxLimit, ReentrantLock lock, Condition condition) {
        this.MAX_LIMIT = maxLimit;
        this.queue = new ArrayBlockingQueue<>(MAX_LIMIT);
        this.lock = lock;
        this.condition = condition;
    }

    public Request get(){
        Request result = null;
        lock.lock();
        try {
            while (queue.isEmpty()) {
                // 请求队列中为空，服务器线程挂起
                condition.await();
            }
            // 请求队列中不为空，服务器线程从队列获取请求，并通知其他挂起的服务器线程
            result = queue.poll();
            condition.signalAll();
        } catch (InterruptedException e) {
            condition.signalAll();
        } finally {
            lock.unlock();
        }
        return result;
    }

    public void put(Request request){
        lock.lock();
        try {
            while (queue.size() >= MAX_LIMIT) {
                // 请求队列已满，客户端线程挂起
                condition.await();
            }
            // 请求队列未满，请求入队，并通知其他等待的客户端线程
            queue.offer(request);
            condition.signalAll();
        } catch (InterruptedException e) {
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

}
