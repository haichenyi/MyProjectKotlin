package com.haichenyi.myproject.utils

import android.support.annotation.StringRes
import android.widget.Toast

import com.haichenyi.myproject.base.MyApplication


/**
 * Author: 海晨忆.
 * Date: 2017/12/21
 * Desc: Toast工具类
 */
class ToastUtils private constructor() {

    init {
        throw RuntimeException("工具类不允许创建对象")
    }

    companion object {
        private var toast: Toast? = null

        private fun init() {
            if (toast == null) {
                synchronized(ToastUtils::class.java) {
                    if (toast == null) {
                        toast = Toast.makeText(MyApplication.getInstance(), "", Toast.LENGTH_SHORT)
                    }
                }
            }
        }

        fun showTipMsg(msg: String) {
            if (null == toast) {
                init()
            }
            toast!!.setText(msg)
            toast!!.show()
        }

        fun showTipMsg(@StringRes msg: Int) {
            if (null == toast) {
                init()
            }
            toast!!.setText(msg)
            toast!!.show()
        }
    }
}
