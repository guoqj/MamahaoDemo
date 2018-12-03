package cn.mamhao.mamahaodemo.utils;

import rx.Subscriber;

/**
 * @author guoqj
 * @version 2.5.4
 * @描述:
 * @Copyright Copyright (c) 2016
 * @Company 昆山妈妈好网络科技有限公司
 * @date 2018/11/30 0030
 */

public class LastTimeSubscriber extends Subscriber<Long> {
    ILastTimesMonitor monitor;

    public LastTimeSubscriber(ILastTimesMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void onCompleted() {
        if (null != monitor)
            monitor.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        System.out.println("onErroronErroronErroronErroronErroronErroronError");
        if (null != monitor)
            monitor.onError();
    }

    @Override
    public void onNext(Long times) {
        if (null != monitor)
            monitor.onNext(times);
    }
}
