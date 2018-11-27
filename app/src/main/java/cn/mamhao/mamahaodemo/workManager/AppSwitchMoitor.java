package cn.mamhao.mamahaodemo.workManager;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import cn.mamhao.mamahaodemo.SharedPreference;

/**
 * @author guoqj
 * @version 2.5.4
 * @描述:检测是否在后台
 * @Copyright Copyright (c) 2016
 * @Company 昆山妈妈好网络科技有限公司
 * @date 2018/11/20 0020
 */

public class AppSwitchMoitor implements Application.ActivityLifecycleCallbacks {

    public int count = 0;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (count == 0) { //后台切换到前台
            System.out.println(">>>>>>>>>>>>>>>>>>>App切到前台");
            SharedPreference.saveToSP(activity,"isAppBgConfig",false);
        }
        count++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        count--;
        if (count == 0) { //前台切换到后台
            System.out.println(">>>>>>>>>>>>>>>>>>>App切到后台");
//           Process.killProcess(Process.myPid());
            SharedPreference.saveToSP(activity,"isAppBgConfig",true);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}
