package cn.mamhao.mamahaodemo.workManager;

import android.os.Process;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import cn.mamhao.mamahaodemo.SharedPreference;

/**
 * @author guoqj
 * @version 2.5.4
 * @描述:
 * @Copyright Copyright (c) 2016
 * @Company 昆山妈妈好网络科技有限公司
 * @date 2018/11/26 0026
 */

public class WorkJob extends Job {
    public static final String TAG = "work_job_tag";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        if (MmhWorkJobStrategy.isMMhWorkJobConfig(getContext())) {
            handleWork();
        }
        return Result.SUCCESS;
    }

    /**
     * 执行任务
     */
    private synchronized void handleWork() {
        //是否在后台和是否在2-5点之间
        if (SharedPreference.getBoolean(getContext(), "isAppBgConfig") && WorkJobUtils.isCurrentInTimeScope(2, 23)) {
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
                    //4、//程序进行杀死  清除所有的activity和进程杀死
                    Process.killProcess(Process.myPid());
                    System.out.println("handleWord  2==========================>" + SharedPreference.getInt(getContext(), "isAppBgConfigNums"));
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
        try {
            //取消之前的全部
            JobManager.instance().cancelAll();
            new JobRequest.Builder(WorkJob.TAG)
                    .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))//设置方案
                    .setUpdateCurrent(true)
                    .build()
                    .schedule();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
