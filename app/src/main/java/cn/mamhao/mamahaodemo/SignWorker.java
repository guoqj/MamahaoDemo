package cn.mamhao.mamahaodemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.format.Time;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * @author guoqj
 * @version 2.5.4
 * @描述:
 * @Copyright Copyright (c) 2016
 * @Company 昆山妈妈好网络科技有限公司
 * @date 2018/11/20 0020
 */

public class SignWorker extends Worker {
    Context context;

    public SignWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        //执行任务
//        handleWord();
//        if(SharedPreference.getBoolean(context, "isAppBgConfig")){
        System.out.println("doWorkdoWork----------------------------------------"+new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒").format(new Date(System.currentTimeMillis())));
////            Process.killProcess(Process.myPid());
        System.out.println(SharedPreference.getBoolean(context, "isAppBgConfig"));
        return Worker.Result.SUCCESS;
    }

    /**
     * 执行任务
     */
    private void handleWord(){
        //是否在后台和是否在2-5点之间
        if (SharedPreference.getBoolean(context, "isAppBgConfig") && isCurrentInTimeScope(2, 5)) {
            switch (SharedPreference.getInt(context, "isAppBgConfigNums")) {
                case 0:
                    //1、第一次到2点-5点之间
                    //2、累计加标识位 1
                    SharedPreference.saveToSP(context, "isAppBgConfigNums", 1);
                    break;
                case 1:
                    //1、第二次次到2点-5点之间
                    //2、累计加标识位 1
                    SharedPreference.saveToSP(context, "isAppBgConfigNums", 2);
                    //3、//程序进行杀死  清除所有的activity和进程杀死

                    //4、累计加标识位 回复到0
                    SharedPreference.saveToSP(context, "isAppBgConfigNums", 0);
                    break;
                default:
                    SharedPreference.saveToSP(context, "isAppBgConfigNums", 0);
                    break;
            }
            //判断当前设备是否在后台
        } else {
            SharedPreference.saveToSP(context, "isAppBgConfigNums", 0);
        }
    }

    /**
     * 判断小时
     * @param targetHour
     * @return
     */
    private boolean compareCurrentHour(int targetHour) {
        int current = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        return current == targetHour;
    }

    /**
     * 判断当前系统时间是否在指定时间的范围内
     *
     * @param beginHour 开始小时，例如22
     * @param endHour   结束小时，例如 8
     * @return true表示在范围内，否则false
     */
    public static boolean isCurrentInTimeScope(int beginHour, int endHour) {
        boolean result = false;
        final long aDayInMillis = 1000 * 60 * 60 * 24;
        final long currentTimeMillis = System.currentTimeMillis();

        Time now = new Time();
        now.set(currentTimeMillis);

        Time startTime = new Time();
        startTime.set(currentTimeMillis);
        startTime.hour = beginHour;
        startTime.minute = 00;

        Time endTime = new Time();
        endTime.set(currentTimeMillis);
        endTime.hour = endHour;
        endTime.minute = 00;

        if (!startTime.before(endTime)) {
// 跨天的特殊情况（比如22:00-8:00）
            startTime.set(startTime.toMillis(true) - aDayInMillis);
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
            Time startTimeInThisDay = new Time();
            startTimeInThisDay.set(startTime.toMillis(true) + aDayInMillis);
            if (!now.before(startTimeInThisDay)) {
                result = true;
            }
        } else {
// 普通情况(比如 8:00 - 14:00)
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
        }
        return result;
    }
}
