package cn.mamhao.mamahaodemo.utils;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author guoqj
 * @version 2.5.4
 * @描述:倒计时管理类 rxjava
 * @Copyright Copyright (c) 2016
 * @Company 昆山妈妈好网络科技有限公司
 * @date 2017/4/1 0001
 */


public class RxjavaLastTimeManage implements ILastTimesMonitor, LifecycleObserver {
    /**
     * 被观察的倒计时
     */
    Observable<Long> observable;
    /**
     * 接口回调
     */
    ILastTimesMonitor monitor;
    /**
     * 倒计时时间
     */
    long times;
    /**
     * 倒计时出错
     */
    boolean isTimeError;
    /**
     * 出错时的时间
     */
    Long firstTimes = 0l;

    public RxjavaLastTimeManage lastTimeConfig(Activity activity, @NonNull long times, @NonNull ILastTimesMonitor monitor) {
        this.times = times;
        this.monitor = monitor;
        observable = countDownTime(times);
        if (null != activity && activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getLifecycle().addObserver(this);
        }
        return this;
    }

    LastTimeSubscriber timeSubscriber;

    /**
     *
     * 开始倒计时
     *
     * @return
     */
    public RxjavaLastTimeManage start() {
        if (null == monitor || null == observable)
            return this;
        initRestoreTimeConfig();
        if (null == timeSubscriber) {
            timeSubscriber = new LastTimeSubscriber(this);
        }
        observable.doOnSubscribe(new Action0() {
            @Override
            public void call() {
                monitor.initConfig(times);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(timeSubscriber);
        return this;
    }

    Long remainTimes;

    public Long getRemainTimes() {
        return remainTimes;
    }

    @Override
    public void initConfig(long times) {
        if (null != monitor)
            monitor.initConfig(times);
    }

    @Override
    public void onCompleted() {
        if (null != monitor)
            monitor.onCompleted();
    }

    @Override
    public void onError() {
        onPauseTime();
        if (null != monitor)
            monitor.onError();
    }

    @Override
    public void onNext(long time) {
        if (null != monitor) {
            if (time > 0) {
                remainTimes = time;
                monitor.onNext(time);
            } else {
                monitor.onCompleted();
            }
        }
    }


    /**
     * 倒计时
     * 获取observable 被观察者 倒计时
     *
     * @param time
     * @return
     */
    public Observable<Long> countDownTime(long time) {
        if (time < 0) time = 0;
        final long countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long increaseTime) {
                        return countTime - increaseTime;
                    }
                })
                .take(countTime, TimeUnit.SECONDS);
    }

    /**
     * 回复倒计时方法
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResumeTime() {
        restoreTime();
        System.out.println("onResumeTimeonResumeTimeonResumeTimeonResumeTime----------------------------------");
    }

    /**
     * 当倒计时暂停时候记录时间
     */
    public void onPauseTime() {
        if (null != timeSubscriber && !timeSubscriber.isUnsubscribed()) {
            timeSubscriber.unsubscribe();
            timeSubscriber = null;
            isTimeError = true;
            firstTimes = System.currentTimeMillis();
            observable = null;
        }
    }

    void initRestoreTimeConfig() {
        isTimeError = false;
        firstTimes = 0l;
    }

    public void restoreTime() {
        if (isTimeError && firstTimes > 0 && remainTimes > 0 && null == timeSubscriber) {
            long time = remainTimes - ((System.currentTimeMillis() - firstTimes) / 1000);
            if (time > 0) {
                times = time;
                observable = countDownTime(time);
                start();
            } else {
                if (null != monitor)
                    monitor.onCompleted();
            }
            initRestoreTimeConfig();
        }
    }

    /**
     * 取消观察者
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destory() {
        if (null != timeSubscriber && !timeSubscriber.isUnsubscribed()) {
            timeSubscriber.unsubscribe();
            timeSubscriber = null;
        }
        if (null != monitor)
            monitor = null;
    }

}
