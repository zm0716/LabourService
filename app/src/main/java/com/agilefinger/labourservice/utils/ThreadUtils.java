package com.agilefinger.labourservice.utils;

/**
 * Created by Shinelon on 2018/3/6.
 */

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * function:两件事情,就是让使用者,一方面可以用线程池,一个方法,就可以把需要子线程运行逻辑放入其中
 * 另一方面,一个方法,就可以把需要主线程运行逻辑放入其中,从而简化了开发中线程调用的冗余代码
 * 使用线程池的技巧:
 * 1.根据自己的开发需求,选用符合自己业务逻辑的线程池
 * 2.在项目中以单例模式使用线程池,线程池本身就是为了减少创建线程的对象,所以一个项目一个线程池就足够
 * 3.核心线程的非典型配置方式:
 * 像非核心线程一样,给核心线程池设置超时时间,当闲置的核心线程闲的太久,一样会被干掉
 * allowCoreThreadTimeOut设置为true即可
 * KeepAliveTime对核心线程生效,给核心线程设置超时时间
 * 当线程池的核心线程几乎没有,对资源的消耗会降到最低,在某些场景下可以考虑给核心线程设置超时时间进行优化
 * 这个工具类涉及到两个知识,线程池,handler的源码
 */
public class ThreadUtils {
    //A.Java内部发招的线程池对象,SingleThreadExecutor里面就维护了一个线程池,
    //资源非常节约,对象是静态,所以整个程序就这一个对象
    public static Executor sExecutors = Executors.newSingleThreadExecutor();
    //如果你的业务需要很多子线程去处理,那么你也没有必要创建太多的线程池对象,顶多把核心线程数量提高就可以满足需求.
    //private static Executor sExecutor=Executors.newFixedThreadPool(10);
    // private static Executor sExecutor=Executors.newScheduledThreadPool(5);

    /**
     * A.外界去使用我的线程池时,暴露一个方法,让他们去执行对应任务.且执行在子线程,可以做耗时操作
     *
     * @param runnable
     */
    public static void runOnSubThread(Runnable runnable) {
        sExecutors.execute(runnable);
    }

    //B.封装是我们可以把子线程里面代码运行到主线程,这个handler不在Activity中new出来的,所以不在主线程
    //B.我们的handler和主线程绑定,英寸我们需要传入主线程的Loop
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    //外界不用写handler,就可以把代码运行到主线程的方法
    public static void runOnMainThread(Runnable runnable) {
        sHandler.post(runnable);
    }

}