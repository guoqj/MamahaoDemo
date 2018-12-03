package cn.mamhao.mamahaodemo.utils;

/**
 * @author guoqj
 * @version 2.5.4
 * @描述:
 * @Copyright Copyright (c) 2016
 * @Company 昆山妈妈好网络科技有限公司
 * @date 2017/4/1 0001
 */


public interface ILastTimesMonitor {

    void initConfig(long times);

    void onCompleted();

    void onError();

    void onNext(long time);
}
