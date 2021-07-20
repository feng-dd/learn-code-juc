package com.feng.juc.week06.myself;

import org.omg.PortableServer.Servant;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/7/20 14:39
 * @Desc 代理类调用器
 * @Since 1.0
 */
public class MyInvocationHandler implements InvocationHandler {

    // 被代理类 servant
    private Object servant;
    // 调度器
    private ExecutorService scheduler;

    public MyInvocationHandler(Object target, ExecutorService scheduler) {
        this.servant = target;
        this.scheduler = scheduler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //todo 可以添加前置处理
        Callable<Object> methodRequest = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                // todo 实际业务逻辑
                return null;
            }
        };
        // todo 可以添加后置处理
        // 返回结果对象Feture，ActivationQueue 为线程池的阻塞队列
        Future<Object> result = scheduler.submit(methodRequest);
        return result;
    }

    public static <T> T newInstance(Class<T> interfaces, ExecutorService scheduler, Servant servant){

        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(servant, scheduler);

        // 通过JDK动态代理生成代理类
        @SuppressWarnings("unchecked")
        T proxy = (T) Proxy.newProxyInstance(interfaces.getClassLoader(), new Class[]{interfaces}, myInvocationHandler);
        return proxy;
    }
}
