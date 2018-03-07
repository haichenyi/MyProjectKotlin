package com.haichenyi.myproject.model.socket

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Author: 海晨忆
 * Date: 2018/2/26
 * Desc:
 */
class SocketService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Thread(Runnable { SocketTcp.getInstance().setPort(8080).setHost("192.168.0.235").connect() }).start()
        Thread(Runnable { SocketUdp.getInstance().setPort(36987).connect() }).start()
    }
}