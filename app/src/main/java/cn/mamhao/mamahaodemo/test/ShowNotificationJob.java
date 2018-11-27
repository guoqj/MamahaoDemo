package cn.mamhao.mamahaodemo.test;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author guoqj
 * @version 2.5.4
 * @描述:
 * @Copyright Copyright (c) 2016
 * @Company 昆山妈妈好网络科技有限公司
 * @date 2018/11/23 0023
 */

public class ShowNotificationJob extends Job {
    public static final String TAG = "show_notification_job_tag";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {

        System.out.println("onRunJobonRunJobonRunJob----------------------------------------" + new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒").format(new Date(System.currentTimeMillis())));
        return Result.SUCCESS;
    }



    public static void schedulePeriodic() {
        new JobRequest.Builder(ShowNotificationJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }

}
