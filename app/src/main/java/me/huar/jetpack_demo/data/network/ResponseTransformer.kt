package me.huar.jetpack_demo.data.network

import com.blankj.utilcode.util.ObjectUtils
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.functions.Function
import me.huar.jetpack_demo.data.network.exception.ApiException
import me.huar.jetpack_demo.data.network.exception.CustomException.handleException
import me.huar.jetpack_demo.model.base.BaseEntry
import me.huar.jetpack_demo.model.base.Response
import me.huar.jetpack_demo.model.entry.EmptyEntry
import org.reactivestreams.Publisher
import retrofit2.HttpException

object ResponseTransformer {
    fun <T> handleResult(): FlowableTransformer<BaseEntry<T>, Response<T>> {
        return FlowableTransformer { upstream: Flowable<BaseEntry<T>> ->
            upstream
                .onErrorResumeNext(ErrorResumeFunction())
                .flatMap(ResponseFunction<T>())
        }
    }

    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
    </T> */
    private class ErrorResumeFunction<T> :
        Function<Throwable?, Publisher<out BaseEntry<T>>> {
        override fun apply(throwable: Throwable): Publisher<out BaseEntry<T>> {
            if (throwable is HttpException) {
                val ex = throwable
            }
            return Flowable.error(handleException(throwable))
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
    </T> */
    private class ResponseFunction<T> :
        Function<BaseEntry<T>, Publisher<Response<T>>> {
        @Throws(Exception::class)
        override fun apply(tBaseEntry: BaseEntry<T>): Publisher<Response<T>> {
            val code = tBaseEntry.code
            val message = tBaseEntry.message
            return if (code == 200) {
                val response: Response<Any?> = Response()
                response.code = code
                response.message = message
                if (ObjectUtils.isEmpty(tBaseEntry.data)) {
                    response.setResponse(EmptyEntry())
                } else {
                    response.setResponse(tBaseEntry.data)
                }
                Flowable.just(response as Response<T>)
            } else {
                Flowable.error(
                    ApiException(
                        code,
                        message!!
                    )
                )
            }
        }
    }
}