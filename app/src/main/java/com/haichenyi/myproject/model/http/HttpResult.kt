package com.haichenyi.myproject.model.http

import com.google.gson.annotations.SerializedName

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:有解析数据的返回
 */
class HttpResult<T> {
    private var code: Int = 0
    private var msg: String? = null
    @SerializedName(value = "result")
    private var data: T? = null

    fun getCode(): Int {
        return code
    }

    fun setCode(code: Int): HttpResult<*> {
        this.code = code
        return this
    }

    fun getMsg(): String? {
        return msg
    }

    fun setMsg(msg: String): HttpResult<*> {
        this.msg = msg
        return this
    }

    fun getData(): T? {
        return data
    }

    fun setData(data: T): HttpResult<*> {
        this.data = data
        return this
    }

    override fun toString(): String {
        return "HttpResult{code=$code, msg='$msg', data=$data}"
    }
}
