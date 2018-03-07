package com.haichenyi.myproject.model.http

import com.haichenyi.myproject.model.http.api.HttpApi
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc: 网络接口Retrofit实现
 */
class RetrofitHelper @Inject
internal constructor(private val httpApi: HttpApi) : HttpHelper {

    override fun loginCode(phone: String): Flowable<HttpNoResult> {
        return httpApi.loginCode(phone)
    }
}
