package com.haichenyi.myproject.model.http

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:没有解析数据的返回
 */
class HttpNoResult {
    private var code: Int = 0
    private var msg: String? = null

    fun getCode(): Int {
        return code
    }

    fun setCode(code: Int): HttpNoResult {
        this.code = code
        return this
    }

    fun getMsg(): String? {
        return msg
    }

    fun setMsg(msg: String): HttpNoResult {
        this.msg = msg
        return this
    }

    override fun toString(): String {
        return "HttpNoResult{code=$code, msg='$msg'}"
    }
}
