package com.haichenyi.myproject.model

import android.app.Activity
import android.util.Log
import com.haichenyi.myproject.model.bean.User
import com.haichenyi.myproject.model.http.HttpHelper
import com.haichenyi.myproject.model.http.HttpNoResult
import com.haichenyi.myproject.model.sql.SqlHelper

import io.reactivex.Flowable

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:
 */
class DataHelper(private val http: HttpHelper, private val sqlHelper: SqlHelper) : HttpHelper, SqlHelper {
    override fun onSelect(name: String?) = sqlHelper.onSelect(name)

    override fun onSelectList(name: String?) = sqlHelper.onSelectList(name)

    override fun onAdd(user: User?) = sqlHelper.onAdd(user)

    override fun onAdd(users: MutableList<User>?) = sqlHelper.onAdd(users)

    override fun onDelete(name: String?) = sqlHelper.onDelete(name)

    override fun onUpdate(oldName: String?,newName: String?) = sqlHelper.onUpdate(oldName,newName)

    override fun loginCode(phone: String): Flowable<HttpNoResult> = http.loginCode(phone)

}

fun Activity.log(txt: String) {
    Log.d("aloe", txt)
}