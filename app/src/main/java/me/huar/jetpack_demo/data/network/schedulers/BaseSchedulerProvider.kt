package me.huar.jetpack_demo.data.network.schedulers

import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler

interface BaseSchedulerProvider {
    fun computation(): Scheduler?
    fun io(): Scheduler?
    fun ui(): Scheduler?
    fun <T> applySchedulers(): FlowableTransformer<T, T>?
}