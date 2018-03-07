package com.haichenyi.myproject.model.http


import com.haichenyi.myproject.base.BaseView
import io.reactivex.subscribers.ResourceSubscriber

/**
 * Author: 海晨忆.
 * Date: 2017/12/21
 * Desc:
 */
abstract class MySubscriber<T> : ResourceSubscriber<T> {
    private var baseView: BaseView? = null
    private var showLoading = false

    constructor(baseView: BaseView) {
        this.baseView = baseView
    }

    constructor(baseView: BaseView, showLoading: Boolean) {
        this.baseView = baseView
        this.showLoading = showLoading
    }

    override fun onStart() {
        super.onStart()
        if (null != baseView && showLoading) {
            baseView!!.showLoading()
        }
    }

    override fun onError(t: Throwable) {
        if (null == baseView) {
            return
        }
        baseView!!.hideLoading()
        if (t is ApiException) {
            when (t.getCode()) {
                HttpCode.NO_PARAMETER -> baseView!!.showTipMsg("参数为空")
                HttpCode.SERVER_ERR -> baseView!!.showTipMsg("服务器错误")
                else -> {
                }
            }
        }
    }

    override fun onComplete() {
        if (null != baseView) {
            baseView!!.hideLoading()
        }
    }
}
