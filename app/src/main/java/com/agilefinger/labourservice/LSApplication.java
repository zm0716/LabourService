package com.agilefinger.labourservice;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StrictMode;
import android.os.Vibrator;

import com.agilefinger.labourservice.cache.OkHttp3Downloader;
import com.agilefinger.labourservice.utils.LocationService;
import com.baidu.mapapi.SDKInitializer;
import com.downloader.PRDownloader;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import me.jessyan.autosize.AutoSizeConfig;

public class LSApplication extends Application {

    public static Context context;
    public LocationService locationService;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initAutoSize();
        initFileDownload();
        initPicasso();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
        try {
            SDKInitializer.initialize(this);
            locationService = new LocationService(getApplicationContext());
            mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPicasso() {
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(getApplicationContext()))//设置disk缓存
                .defaultBitmapConfig(Bitmap.Config.RGB_565) // 设置全局的图片样式
                .loggingEnabled(false)
                .build();
        Picasso.setSingletonInstance(picasso); // 设置Picasso单例
    }

    private void initFileDownload() {
        PRDownloader.initialize(getApplicationContext());
    }

    private void initAutoSize() {
        AutoSizeConfig.getInstance()
                //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
                //如果没有这个需求建议不开启
                .setCustomFragment(false)
                //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
                .setLog(true)
                //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
                //AutoSize 会将屏幕总高度减去状态栏高度来做适配, 如果设备上有导航栏还会减去导航栏的高度,设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏以及导航栏高度
                //.setUseDeviceSize(true)
                //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
                .setBaseOnWidth(true);
        AutoSizeConfig.getInstance().getUnitsManager().setSupportSP(false);
    }

    public static List<Activity> activityList = new LinkedList<Activity>();

    /**
     * 添加到Activity容器中
     */
    public static void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    /**
     * 便利所有Activigty并finish
     */
    public static void finishActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    /**
     * 结束指定的Activity
     */
    public static void finishSingleActivity(Activity activity) {
        if (activity != null) {
            if (activityList.contains(activity)) {
                activityList.remove(activity);
            }
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity 在遍历一个列表的时候不能执行删除操作，所有我们先记住要删除的对象，遍历之后才去删除。
     */
    public static void finishSingleActivityByClass(Class<?> cls) {
        Activity tempActivity = null;
        for (Activity activity : activityList) {
            if (activity.getClass().equals(cls)) {
                tempActivity = activity;
            }
        }

        finishSingleActivity(tempActivity);
    }

}