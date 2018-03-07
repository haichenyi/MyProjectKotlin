package com.haichenyi.myproject.base

import android.support.annotation.StringRes

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:
 */
interface BaseView {
    fun showTipMsg(msg: String)

    fun showTipMsg(@StringRes msg: Int)

    fun showLoading()

    fun hideLoading()

    fun invalidToken()

    fun myFinish()
}
