package cn.mamhao.mamahaodemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import cn.mamhao.mamahaodemo.workManager.WorkJob;

public class MainActivity extends AppCompatActivity {
    String workName = "mmh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityListUtil.getInstence().addActivityToList(this);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                WorkerManageUtils.oneWork();
//                ShowNotificationTestJob.schedulePeriodic();
                startActivity(new Intent(MainActivity.this, TwoActivity.class));
            }
        });
//        System.out.println("test  ==============>" + isCurrentInTimeScope(13, 00, 13, 21));
//        test1();
//        testttt();
//        System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
//        WorkerManageUtils.startWork();
        WorkJob.schedulePeriodic();
    }

    int REQUEST_CODE = 20;
    private static final int INTERVAL = 1000 * 60 * 60 * 24;// 24h

    public void test() {
        Intent intent = new Intent(MainActivity.this, LoopService.class);
        PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this,
                REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        // Schedule the alarm!
        AlarmManager am = (AlarmManager) MainActivity.this
                .getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 10);
        calendar.set(Calendar.MILLISECOND, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                INTERVAL, sender);
    }

    public void test1() {
        AlarmManager manager = (AlarmManager) MainActivity.this.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this.getApplicationContext(), LoopService.class);
        intent.setAction(LoopService.ACTION);
        PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this.getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // long triggerAtTime = SystemClock.elapsedRealtime() + 1000;
        /**
         * 闹钟的第一次执行时间，以毫秒为单位，可以自定义时间，不过一般使用当前时间。需要注意的是，本属性与第一个属性（type）密切相关，
         * 如果第一个参数对应的闹钟使用的是相对时间（ELAPSED_REALTIME和ELAPSED_REALTIME_WAKEUP），那么本属性就得使用相对时间（相对于系统启动时间来说），
         *      比如当前时间就表示为：SystemClock.elapsedRealtime()；
         * 如果第一个参数对应的闹钟使用的是绝对时间（RTC、RTC_WAKEUP、POWER_OFF_WAKEUP），那么本属性就得使用绝对时间，
         *      比如当前时间就表示为：System.currentTimeMillis()。
         */
        manager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 100, pendingIntent);
    }

    /**
     * 判断当前系统时间是否在指定时间的范围内
     *
     * @param beginHour 开始小时，例如22
     * @param beginMin  开始小时的分钟数，例如30
     * @param endHour   结束小时，例如 8
     * @param endMin    结束小时的分钟数，例如0
     * @return true表示在范围内，否则false
     */
    public static boolean isCurrentInTimeScope(int beginHour, int beginMin, int endHour, int endMin) {
        boolean result = false;
        final long aDayInMillis = 1000 * 60 * 60 * 24;
        final long currentTimeMillis = System.currentTimeMillis();

        Time now = new Time();
        now.set(currentTimeMillis);

        Time startTime = new Time();
        startTime.set(currentTimeMillis);
        startTime.hour = beginHour;
        startTime.minute = beginMin;

        Time endTime = new Time();
        endTime.set(currentTimeMillis);
        endTime.hour = endHour;
        endTime.minute = endMin;

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

    public void testttt() {
        //获取一个builder
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(SignWorker.class, 15, TimeUnit.MINUTES)
                .build();
//插入worker队列，并且使用enqueueUniquePeriodicWork方法，防止重复
        WorkManager.getInstance().enqueueUniquePeriodicWork(workName, ExistingPeriodicWorkPolicy.KEEP, request);
    }

}
