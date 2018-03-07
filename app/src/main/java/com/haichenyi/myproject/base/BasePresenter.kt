package com.haichenyi.myproject.base

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:
 */
interface BasePresenter<T : BaseView> {
    fun attachView(baseView: T)

    fun detachView()
}
