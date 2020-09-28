package me.huar.jetpack_demo.data.network.schedulers;

import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;

public interface BaseSchedulerProvider {

    public Scheduler computation();

    public Scheduler io();

    public Scheduler ui();

    public <T> FlowableTransformer<T, T> applySchedulers();

}
