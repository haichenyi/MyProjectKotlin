package com.haichenyi.myproject.model.socket

import com.haichenyi.myproject.model.bean.SocketTcpBean
import com.haichenyi.myproject.model.bean.SocketUdpBean
import org.greenrobot.eventbus.EventBus

/**
 * Author: 海晨忆
 * Date: 2018/2/26
 * Desc:
 */
object SocketUtils {
    fun sendTcpMsg(msg: String) {
        sendTcpMsg(SocketTcpBean(msg))
    }

    /**
     * 服务器的ip
     */
    fun sendUdpMsg(host: String, port: Int, msg: String) {
        sendUdpMsg(SocketUdpBean(host,port,msg.toByteArray()))
    }

    private fun sendUdpMsg(socketUdpBean: SocketUdpBean) {
        EventBus.getDefault().post(socketUdpBean)
    }

    private fun sendTcpMsg(socketTcpBean: SocketTcpBean) {
        EventBus.getDefault().post(socketTcpBean)
    }
}