package com.haichenyi.myproject.model.http

import android.support.v4.util.ArrayMap
import com.google.gson.Gson
import com.haichenyi.myproject.common.Constants
import com.haichenyi.myproject.utils.LogUtils
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.nio.charset.Charset

/**
 * Author: 海晨忆.
 * Date: 2017/12/21
 * Desc: 网络拦截器
 */
class MyHttpInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()//获取request对象
        val body = request.body() ?: return chain.proceed(request)//获取body
        if (body is FormBody) {
            val proceed = chain.proceed(formatRequest(request))
            val responseBody = proceed.body()
            if (null != responseBody) {
                val source = responseBody.source()
                source.request(java.lang.Long.MAX_VALUE)
                val buffer = source.buffer()
                val responseStr = buffer.clone().readString(Charset.forName("UTF-8"))
                LogUtils.d(Constants.tagHttp, requireNotNull(responseStr))
            }
            return proceed
        } else {
            return chain.proceed(request)
        }
    }

    private fun formatRequest(request: Request): Request {
        val formBody = request.body() as FormBody? ?: return request//获取body
        val size = formBody.size()
        val arrayMap = ArrayMap<String, String>()
        for (i in 0 until size) {
            arrayMap.put(formBody.name(i), formBody.value(i))
        }
        arrayMap.put("token", "BDBB4A35E564116E97D9A1C4763571FD")//到时候传衣柜id，现在随便传
        var param = Gson().toJson(arrayMap)
        param = param.replace("\"[", "[").replace("]\"", "]")
                .replace("\"{", "{").replace("}\"", "}")
                .replace("\\", "")
        LogUtils.d(Constants.tagHttp, request.url().toString() + "\n" + param);
        return Request.Builder()
                .url(request.url())
                .headers(request.headers())
                .post(FormBody.Builder()
                        .add("json", param)
                        .build())
                .build()
    }
}
