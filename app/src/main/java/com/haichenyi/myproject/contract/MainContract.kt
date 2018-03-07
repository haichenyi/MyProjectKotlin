package com.haichenyi.myproject.contract

import com.haichenyi.myproject.base.BasePresenter
import com.haichenyi.myproject.base.BaseView
import com.haichenyi.myproject.model.bean.User

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:
 */
interface MainContract {
    interface IView : BaseView
    interface Presenter : BasePresenter<IView> {
        fun loadData()
        fun onAdd(user: User)
        fun onAddList(users: MutableList<User>)
        fun onSelect(name: String): User
        fun onSelectList(name: String): MutableList<User>
        fun onDelete(name: String)
        fun onUpdate(oldName: String,newName:String)
    }
}
