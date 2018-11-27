package cn.mamhao.mamahaodemo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;

/**
 * @author guoqj
 * @version 2.5.4
 * @描述:
 * @Copyright Copyright (c) 2016
 * @Company 昆山妈妈好网络科技有限公司
 * @date 2018/11/21 0021
 */

public class WorkerManageUtils {
    private final static String workId = "mamahaoWorkId";

    /**
     * 启动一个轮询任务
     */
    public static void startWork() {
        stopWork();
        //获取一个builder
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(false)//不在电量不足时执行
                .setRequiresCharging(true)//在充电时执行
                .setRequiresStorageNotLow(false)//不在存储容量不足时执行
                .build();
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(SignWorker.class, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS).setConstraints(constraints)
                .build();
//插入worker队列，并且使用enqueueUniquePeriodicWork方法，防止重复
        WorkManager.getInstance().enqueueUniquePeriodicWork(workId, ExistingPeriodicWorkPolicy.REPLACE, request);

        LiveData<WorkStatus> ddd=WorkManager.getInstance().getStatusByIdLiveData(request.getId());
        ddd.observeForever(new Observer<WorkStatus>() {
            @Override
            public void onChanged(@Nullable WorkStatus workStatus) {
                System.out.println(workStatus.getState()+"  ====onChanged=========================");
            }
        });
//        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1,new BasicThreadFactory.Builder().namingPattern("scheduled-pool-%d").daemon(true).build());
//        scheduledExecutorService.scheduleAtFixedRate(runnable, 5, 10, TimeUnit.SECONDS);
    }

    /**
     * 停止一个轮询任务
     */
    public static void stopWork() {
        WorkManager.getInstance().cancelUniqueWork(workId);
    }

    public static void oneWork() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(false)//不在电量不足时执行
                .setRequiresCharging(true)//在充电时执行
                .setRequiresStorageNotLow(false)//不在存储容量不足时执行
                .build();
        //3.构造work
        OneTimeWorkRequest httpwork = new OneTimeWorkRequest.Builder(SignWorker.class).setConstraints(constraints).build();
        //4.放入执行队列
        WorkManager.getInstance().enqueue(httpwork);
    }



}
