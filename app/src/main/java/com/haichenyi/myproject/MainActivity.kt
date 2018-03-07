package com.haichenyi.myproject

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.View
import android.widget.Button
import com.haichenyi.myproject.base.BaseMvpActivity
import com.haichenyi.myproject.contract.MainContract
import com.haichenyi.myproject.model.bean.User
import com.haichenyi.myproject.model.socket.SocketService
import com.haichenyi.myproject.model.socket.SocketUtils
import com.haichenyi.myproject.presenter.MainPresenter
import com.haichenyi.myproject.utils.LogUtils
import com.haichenyi.myproject.utils.RxPerUtils
import com.haichenyi.myproject.utils.ToastUtils
import java.io.File


class MainActivity : BaseMvpActivity<MainPresenter>(), MainContract.IView {
    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        super.initData()
        initToolbar(true, false, true).setMyTitle("主页").setMoreTitle("更多")
        val btnAdd = findViewById<Button>(R.id.btn_add)
        val btnDelete = findViewById<Button>(R.id.btn_delete)
        val btnUpdate = findViewById<Button>(R.id.btn_update)
        val btnSelect = findViewById<Button>(R.id.btn_select)
        val btnTest = findViewById<Button>(R.id.btn_test)
        val btnStart = findViewById<Button>(R.id.btn_socket_start)
        val btnSend = findViewById<Button>(R.id.btn_tcp_send)
        val btnSend1 = findViewById<Button>(R.id.btn_udp_send)
        val btnPermission = findViewById<Button>(R.id.btn_permission)
        setOnClick(btnPermission, btnSend, btnSend1, btnStart, btnAdd, btnDelete, btnUpdate, btnSelect, btnTest)
    }

    override fun initInject() {
        activityComponent.inject(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v!!.id) {
            R.id.btn_add -> {
//                basePresenter.onAdd(User("小红", 1, 165, "50KG", 18))
                val dataList = mutableListOf<User>()
                dataList.add(User(null, "小刚", 2, 175, "60KG", 28))
                dataList.add(User(null, "小东", 2, 185, "65KG", 25))
                basePresenter.onAddList(dataList)
            }
            R.id.btn_delete -> {
//                basePresenter.onDelete("小红")
                basePresenter.onDelete("小东")
            }
            R.id.btn_update -> {
                basePresenter.onUpdate("小刚", "小贝")
            }
            R.id.btn_select -> {
                val user = basePresenter.onSelect("小红")
                user?.let {
                    println(it.name)
                }
                val users = basePresenter.onSelectList("小东")
                users?.forEach {
                    println(it.name)
                }
            }
            R.id.btn_test -> basePresenter.loadData()
            R.id.btn_socket_start -> startService(Intent(this, SocketService::class.java))
            R.id.btn_tcp_send -> SocketUtils.sendTcpMsg("this is Client")
            R.id.btn_udp_send -> SocketUtils.sendUdpMsg("192.168.0.235", 5635, "this is udp Client")
            R.id.btn_permission -> RxPerUtils.requestPermission(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                if (it) {
                    //这里写你自己的逻辑，已经获得权限，做你自己的业务逻辑操作
                    startCamera()
                } else {
                    RxPerUtils.setupPermission(this, "相机和存储",
                            "相机功能") {
                    }
                }
            }
        }
    }

    private var file: File? = null
    var imageUri: Uri? = null
    private fun startCamera() {
        file = File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg")
        if (!file!!.parentFile.exists()) file!!.parentFile.mkdirs()
        //变化
        imageUri = FileProvider.getUriForFile(this, "com.haichenyi.myproject.fileprovider", file)//通过FileProvider创建一个content类型的Uri
        val intent = Intent()
        //变化
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.action = MediaStore.ACTION_IMAGE_CAPTURE//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)//将拍取的照片保存到指定URI
        startActivityForResult(intent, 1006)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val absolutePath = file!!.absolutePath
        val imageUri1 = imageUri
        LogUtils.v("WZ","file!!.absolutePath:$absolutePath")
        LogUtils.v("WZ","imageUri:$imageUri1")
        ToastUtils.showTipMsg("456")
        data?.let {
            ToastUtils.showTipMsg("123")
        }
    }
}


