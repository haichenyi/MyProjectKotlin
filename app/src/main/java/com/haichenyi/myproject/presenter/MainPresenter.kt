package com.haichenyi.myproject.presenter

import com.haichenyi.myproject.base.BaseMvpPresenter
import com.haichenyi.myproject.base.MyApplication
import com.haichenyi.myproject.contract.MainContract
import com.haichenyi.myproject.model.DataHelper
import com.haichenyi.myproject.model.bean.User
import com.haichenyi.myproject.model.http.HttpNoResult
import com.haichenyi.myproject.model.http.MyRxUtils
import com.haichenyi.myproject.model.http.MySubscriber
import com.haichenyi.myproject.utils.ToastUtils
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:
 */
class MainPresenter @Inject
internal constructor() : BaseMvpPresenter<MainContract.IView>(), MainContract.Presenter {
    override fun onUpdate(oldName: String,newName:String) = dataHelper.onUpdate(oldName, newName)

    override fun onDelete(name: String) = dataHelper.onDelete(name)

    override fun onSelect(name: String) = dataHelper.onSelect(name)

    override fun onSelectList(name: String) = dataHelper.onSelectList(name)

    override fun onAdd(user: User) = dataHelper.onAdd(user)

    override fun onAddList(users: MutableList<User>) = dataHelper.onAdd(users)

    private val dataHelper: DataHelper

    init {
        dataHelper = MyApplication.getAppComponent().dataHelper
    }

    override fun loadData() {
        addSubscribe(dataHelper.loginCode("13412345678")
                .compose(MyRxUtils.toMain(Schedulers.io()))
                .subscribeWith(object : MySubscriber<HttpNoResult>(baseView, true) {
                    override fun onNext(httpNoResult: HttpNoResult) {
                        ToastUtils.showTipMsg(httpNoResult.getCode().toString())
                    }
                }))
    }


}
