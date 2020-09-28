package me.huar.jetpack_demo.data.network.schedulers

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider  // Prevent direct instantiation.
private constructor() : BaseSchedulerProvider {
    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun <T> applySchedulers(): FlowableTransformer<T, T> {
        return FlowableTransformer { flowable: Flowable<T> ->
            flowable.subscribeOn(io())
                .observeOn(ui())
        }
    }

    fun <T> applySchedulers2(): SingleTransformer<T, T> {
        return SingleTransformer { upstream: Single<T> ->
            upstream.subscribeOn(io())
                .observeOn(ui())
        }
    }

    fun <T> applySchedulers3(): SingleTransformer<T, T> {
        return SingleTransformer { upstream: Single<T> ->
            upstream.subscribeOn(io())
                .observeOn(io())
        }
    }

    companion object {
        private var INSTANCE: SchedulerProvider? = null

        @get:Synchronized
        val instance: SchedulerProvider?
            get() {
                if (INSTANCE == null) {
                    INSTANCE = SchedulerProvider()
                }
                return INSTANCE
            }
    }
}