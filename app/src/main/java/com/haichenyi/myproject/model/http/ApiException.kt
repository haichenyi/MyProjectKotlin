package com.haichenyi.myproject.model.http

/**
 * Author: 海晨忆.
 * Date: 2017/12/21
 * Desc: 接口异常判断处理
 */
class ApiException : Exception {
    private var code: Int = 0

    constructor(code: Int) {
        this.code = code
    }

    constructor(code: Int, message: String) : super(message) {
        this.code = code
    }

    fun getCode(): Int {
        return code
    }

    fun setCode(code: Int): ApiException {
        this.code = code
        return this
    }
}
