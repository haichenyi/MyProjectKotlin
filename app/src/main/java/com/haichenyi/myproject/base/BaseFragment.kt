package com.haichenyi.myproject.base

import android.os.Bundle
import android.support.annotation.IdRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.haichenyi.myproject.utils.ToastUtils

import me.yokeyword.fragmentation.SupportFragment

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:
 */
abstract class BaseFragment : SupportFragment(), BaseView, View.OnClickListener {
    protected var isInit: Boolean = false
    private var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layoutRes = layoutRes()
        return if (0 != layoutRes) {
            inflater.inflate(layoutRes, null)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        isInit = true
        init()
    }

    protected fun <T : View> findViewById(@IdRes id: Int): T {
        return rootView!!.findViewById(id)
    }

    /**
     * 设置点击事件.
     *
     * @param ids 被点击View的ID
     * @return [BaseFragment]
     */
    fun setOnClick(@IdRes vararg ids: Int): BaseFragment {
        for (id in ids) {
            rootView!!.findViewById<View>(id).setOnClickListener(this)
        }
        return this
    }

    /**
     * 设置点击事件.
     *
     * @param views 被点击View的ID
     * @return [BaseFragment]
     */
    fun setOnClick(vararg views: View): BaseFragment {
        for (view in views) {
            view.setOnClickListener(this)
        }
        return this
    }

    protected abstract fun init()

    override fun onDestroy() {
        rootView = null
        super.onDestroy()
    }

    protected abstract fun layoutRes(): Int

    override fun showTipMsg(msg: String) {
        ToastUtils.showTipMsg(msg)
    }

    override fun showTipMsg(msg: Int) {
        ToastUtils.showTipMsg(msg)
    }

    override fun showLoading() {
        val activity = activity as BaseActivity
        (activity as? BaseMvpActivity<*>)?.showLoading()
    }

    override fun hideLoading() {
        val activity = activity as BaseActivity
        (activity as? BaseMvpActivity<*>)?.hideLoading()
    }

    override fun invalidToken() {
        val activity = activity as BaseActivity
        (activity as? BaseMvpActivity<*>)?.invalidToken()
    }

    override fun onClick(v: View) {}

    override fun myFinish() {
        onBackPressedSupport()
    }
}

