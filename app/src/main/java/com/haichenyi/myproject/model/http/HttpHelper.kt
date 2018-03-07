package com.haichenyi.myproject.model.http

import io.reactivex.Flowable

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:网络接口，接口参数Token统一处理，方法中不传Token
 */
interface HttpHelper {
    /**
     * 登录时获取验证码.
     *
     * @param phone 手机号
     * @return {"code":0}
     */
    fun loginCode(phone: String): Flowable<HttpNoResult>
    /*Flowable<HttpResult<Login>> login(String phone, String code);
  Flowable<HttpResult<List<DiyBean>>> diyKeys(String allId);*/
}
