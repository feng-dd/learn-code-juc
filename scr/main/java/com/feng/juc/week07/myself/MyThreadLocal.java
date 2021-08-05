package com.feng.juc.week07.myself;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/8/5 10:59
 * @Desc 自己实现的ThreadLocal
 * @Since 1.0
 */
public class MyThreadLocal<V> {

    // 由于Map持有线程的引用，当线程执行完毕，只要MyThreadLocal对象存货，线程对象无法被回收，造成内存泄露
    // 内存泄露：动态分配的堆内存无法被释放，导致内存浪费
    private ConcurrentHashMap<Thread, V> concurrentHashMap = new ConcurrentHashMap<>();

    public V get(){
        return concurrentHashMap.get(Thread.currentThread());
    }

    public void set(V value){
        concurrentHashMap.put(Thread.currentThread(), value);
    }

    public V get(Thread t){
        return concurrentHashMap.get(t);
    }

    public void set(Thread t, V value){
        concurrentHashMap.put(t, value);
    }

}
