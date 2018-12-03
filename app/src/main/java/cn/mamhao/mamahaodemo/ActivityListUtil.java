package cn.mamhao.mamahaodemo;

import android.app.Activity;

import java.util.ArrayList;

/**
 * @author guoqj
 * @version 2.5.4
 * @描述:
 * @Copyright Copyright (c) 2016
 * @Company 昆山妈妈好网络科技有限公司
 * @date 2018/11/20 0020
 */

public class ActivityListUtil {
    private static ActivityListUtil instence;
    public ArrayList<Activity> activityList;

    public ActivityListUtil() {
        activityList = new ArrayList<Activity>();
    }

    public static ActivityListUtil getInstence() {
        if (instence == null) {
            instence = new ActivityListUtil();
        }
        return instence;
    }

    public void addActivityToList(Activity activity) {
        if (activity != null) {
            activityList.add(activity);
        }
    }

    public void removeActivityFromList(Activity activity) {
        if (activityList != null && activityList.size() > 0) {
            activityList.remove(activity);
        }
    }

    public void cleanActivityList() {
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                Activity activity = activityList.get(i);
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        }

    }
}
