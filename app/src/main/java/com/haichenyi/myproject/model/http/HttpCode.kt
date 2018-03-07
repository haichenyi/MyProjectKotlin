package com.haichenyi.myproject.model.http

/**
 * Author: 海晨忆.
 * Date: 2017/12/21
 * Desc: 网络请求状态码
 */
interface HttpCode {
    companion object {
        /**
         * 成功.
         */
        val SUCCESS = 0
        /**
         * 参数为空.
         */
        val NO_PARAMETER = 1
        /**
         * 服务器错误.
         */
        val SERVER_ERR = 3
    }
}
