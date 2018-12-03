package cn.mamhao.mamahaodemo.workManager;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * @author guoqj
 * @version 2.5.4
 * @描述:
 * @Copyright Copyright (c) 2016
 * @Company 昆山妈妈好网络科技有限公司
 * @date 2018/11/23 0023
 */

public class WorkJobCreatorFactory implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            case WorkJob.TAG:
                return new WorkJob();
            default:
                return null;
        }
    }
}
