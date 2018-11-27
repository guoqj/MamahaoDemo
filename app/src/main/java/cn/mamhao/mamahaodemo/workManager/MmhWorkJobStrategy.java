package cn.mamhao.mamahaodemo.workManager;

import android.content.Context;

import com.evernote.android.job.JobManager;

/**
 * @author guoqj
 * @version 2.5.4
 * @描述:
 * @Copyright Copyright (c) 2016
 * @Company 昆山妈妈好网络科技有限公司
 * @date 2018/11/26 0026
 */

public class MmhWorkJobStrategy {

    /**
     * 初始化 兜底测试 后台服务
     */
    public static void initWorkJob(Context context) {
        try {
            JobManager.create(context).addJobCreator(new WorkJobCreatorFactory());
        } catch (Exception e) {
        }
    }

    /**
     * 启动兜底
     */
    public static void startWork() {
        WorkJob.schedulePeriodic();
    }

    /**
     * 停止
     */
    public static void stopWork() {
        JobManager.instance().cancelAll();
    }
}
