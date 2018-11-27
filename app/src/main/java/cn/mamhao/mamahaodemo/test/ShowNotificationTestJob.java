package cn.mamhao.mamahaodemo.test;

import android.os.Process;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.sql.Date;
import java.text.SimpleDateFormat;

import cn.mamhao.mamahaodemo.ActivityListUtil;
import cn.mamhao.mamahaodemo.SharedPreference;
import cn.mamhao.mamahaodemo.workManager.WorkJobUtils;

/**
 * @author guoqj
 * @version 2.5.4
 * @描述:
 * @Copyright Copyright (c) 2016
 * @Company 昆山妈妈好网络科技有限公司
 * @date 2018/11/23 0023
 */

public class ShowNotificationTestJob extends Job {

    public static final String TAG = "show_notification_job_test_tag";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
//        if (!SharedPreference.getBoolean(getContext(), "isAppBgConfig") && WorkJobUtils.isCurrentInTimeScope(14, 15)) {
//            System.out.println(" =------------------->>>"+true);
//        } else {
//            System.out.println(" =------------------->>>"+false);
//        }
        handleWork();
//        System.out.println("ShowNotificationTestJob----------------------------------------" + new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒").format(new Date(System.currentTimeMillis())));
        return Result.SUCCESS;
    }
    /**
     * 执行任务
     */
    private synchronized void handleWork() {
        //是否在后台和是否在2-5点之间
        if (WorkJobUtils.isCurrentInTimeScope(2, 23)) {
            switch (SharedPreference.getInt(getContext(), "isAppBgConfigNums")) {
                case 0:
                    //1、第一次到2点-5点之间
                    //2、累计加标识位 1
                    SharedPreference.saveToSP(getContext(), "isAppBgConfigNums", 1);
                    System.out.println("handleWord  1==========================>" + SharedPreference.getInt(getContext(), "isAppBgConfigNums"));
                    break;
                case 1:
                    System.out.println("handleWord  2==========================>" + SharedPreference.getInt(getContext(), "isAppBgConfigNums"));
                    //1、第二次次到2点-5点之间
                    //2、累计加标识位 1
                    SharedPreference.saveToSP(getContext(), "isAppBgConfigNums", 2);
                    //3、累计加标识位 回复到0
                    SharedPreference.saveToSP(getContext(), "isAppBgConfigNums", 0);
                    System.out.println("handleWord  2==========================>" + SharedPreference.getInt(getContext(), "isAppBgConfigNums"));
                    //4、//程序进行杀死  清除所有的activity和进程杀死
                    ActivityListUtil.getInstence().cleanActivityList();
                    Process.killProcess(Process.myPid());
                    break;
                default:
                    //如果是其他了就 清零
                    SharedPreference.saveToSP(getContext(), "isAppBgConfigNums", 0);
                    break;
            }
            //判断当前设备是否在后台
        } else {
            System.out.println(SharedPreference.getInt(getContext(), "isAppBgConfigNums") + "  ===WorkJob----------------------------------------" + new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒").format(new Date(System.currentTimeMillis())));
            SharedPreference.saveToSP(getContext(), "isAppBgConfigNums", 0);
        }
    }
    public static void schedulePeriodic() {
        new JobRequest.Builder(ShowNotificationTestJob.TAG)
                .startNow()
                .build()
                .schedule();
//        int jobId = new JobRequest.Builder(ShowNotificationTestJob.TAG)
//                .setExecutionWindow(30_000L, 40_000L)
//                .setBackoffCriteria(5_000L, JobRequest.BackoffPolicy.EXPONENTIAL)
//                .setRequiresCharging(true)
//                .setRequiresDeviceIdle(false)
//                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
//                .setRequirementsEnforced(true)
//                .setUpdateCurrent(true)
//                .build()
//                .schedule();
//        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(this, TestService.class));  //指定哪个JobService执行操作
//        builder.setMinimumLatency(TimeUnit.MILLISECONDS.toMillis(10)); //执行的最小延迟时间
//        builder.setOverrideDeadline(TimeUnit.MILLISECONDS.toMillis(15));  //执行的最长延时时间
//        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NOT_ROAMING);  //非漫游网络状态
//        builder.setBackoffCriteria(TimeUnit.MINUTES.toMillis(10), JobInfo.BACKOFF_POLICY_LINEAR);  //线性重试方案
//        builder.setRequiresCharging(false); // 未充电状态
//        jobScheduler.schedule(builder.build());
    }

}
