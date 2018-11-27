package cn.mamhao.mamahaodemo;

import android.app.Application;

import com.evernote.android.job.JobManager;

import cn.mamhao.mamahaodemo.workManager.AppSwitchMoitor;
import cn.mamhao.mamahaodemo.workManager.WorkJobCreatorFactory;

/**
 * @author guoqj
 * @version 2.5.4
 * @描述:
 * @Copyright Copyright (c) 2016
 * @Company 昆山妈妈好网络科技有限公司
 * @date 2018/11/20 0020
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        try {
            registerActivityLifecycleCallbacks(new AppSwitchMoitor());
            JobManager.create(this).addJobCreator(new WorkJobCreatorFactory());
        }catch (Exception e){
        }
        //JobManager.instance().getConfig().setAllowSmallerIntervalsForMarshmallow(true); // Don't use this in production
       // JobManager.create(this).set
    }
}
