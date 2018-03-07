package com.haichenyi.myproject.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.support.v7.app.AlertDialog
import com.haichenyi.myproject.R
import com.tbruyelle.rxpermissions2.RxPermissions
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

/**
 * Author: 海晨忆
 * Date: 2018/2/28
 * Desc:
 */
object RxPerUtils {
    /**
     * 请求权限的获取方法
     * activity：Activity对象
     * permissions：需要获取的权限，可以传多个
     * aloe: (b: Boolean)：一个参数的回调方法，b为true，表示用户给了权限，false，表示没有给权限
     */
    fun requestPermission(activity: Activity, vararg permissions: String, aloe: (b: Boolean) -> Unit) {
        RxPermissions(activity).request(*permissions)
                .subscribe { aBoolean -> aloe(aBoolean) }
    }

    /**
     * 当用户拒绝给权限的时候调用，跳转权限设置页面，让用户手动给权限
     * activity：Activity对象
     * permissionName：权限名称
     * msg：提示信息
     */
    fun setupPermission(activity: Activity, permissionName: String, msg: String,
                        aloe: () -> Unit) {
        AlertDialog.Builder(activity, R.style.Theme_AppCompat_Dialog).setTitle("权限申请")
                .setMessage(String.format(Locale.getDefault(),
                        "请在“权限”中开启“%1s权限”，以正常使用%2s", permissionName, msg))
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel) { dialog, which -> aloe }.setPositiveButton("去设置") { dialog, which ->
            //小米的设置页面跟其他手机不一样，所以，要判断出来
            if (isMiUi()) {
                setMiUiPermissions(activity)
            } else {
                activity.startActivityForResult(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(Uri.fromParts("package", activity.packageName, null)), 1000)
            }
        }.create().show()
    }

    private fun setMiUiPermissions(activity: Activity) {
        if (isMiUi()) {
            try {
                // MIUI 8
                activity.startActivityForResult(Intent("miui.intent.action.APP_PERM_EDITOR")
                        .setClassName("com.miui.securitycenter",
                                "com.miui.permcenter.permissions.PermissionsEditorActivity")
                        .putExtra("extra_pkgname", activity.packageName), 1000)
            } catch (e: Exception) {
                try {
                    // MIUI 5/6/7
                    activity.startActivityForResult(Intent("miui.intent.action.APP_PERM_EDITOR")
                            .setClassName("com.miui.securitycenter",
                                    "com.miui.permcenter.permissions.AppPermissionsEditorActivity")
                            .putExtra("extra_pkgname", activity.packageName), 1000)
                } catch (e1: Exception) {
                    // 否则跳转到应用详情
                    activity.startActivityForResult(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            .setData(Uri.fromParts("package", activity.packageName,
                                    null)), 1000)
                }
            }
        }
    }

    private fun isMiUi(): Boolean {
        val device = Build.MANUFACTURER
        if (device == "Xiaomi") {
            try {
                val prop = Properties()
                prop.load(FileInputStream(File(Environment.getRootDirectory(), "build.prop")))
                return (prop.getProperty("ro.miui.ui.version.code", null) != null
                        || prop.getProperty("ro.miui.ui.version.name", null) != null
                        || prop.getProperty("ro.miui.internal.storage", null) != null)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return false
    }
}