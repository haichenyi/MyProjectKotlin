package com.haichenyi.myproject.model.http

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:
 */
interface ProtocolHttp {
    companion object {
        const val HTTP_HOST = "http://192.168.3.132:8010/app/"
        const val HTTP_COMMON = "war/"
        const val METHOD_LOGIN_CODE = HTTP_COMMON + "selectModelList"//测试接口
    }
}
