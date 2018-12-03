//package cn.mamhao.mamahaodemo;
//
//import com.evernote.android.job.Job;
//import com.evernote.android.job.JobCreator;
//
//import cn.mamhao.mamahaodemo.test.ShowNotificationJob;
//import cn.mamhao.mamahaodemo.test.ShowNotificationTestJob;
//
///**
// * @author guoqj
// * @version 2.5.4
// * @描述:
// * @Copyright Copyright (c) 2016
// * @Company 昆山妈妈好网络科技有限公司
// * @date 2018/11/23 0023
// */
//
//public class DemoJobCreator implements JobCreator {
//
//    @Override
//    public Job create(String tag) {
//        switch (tag) {
//            case ShowNotificationJob.TAG:
//                return new ShowNotificationJob();
//            case ShowNotificationTestJob
//                        .TAG:
//                return new ShowNotificationTestJob();
//            default:
//                return null;
//        }
//    }
//}
