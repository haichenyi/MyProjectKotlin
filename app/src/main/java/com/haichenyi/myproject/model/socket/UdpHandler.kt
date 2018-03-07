package com.haichenyi.myproject.model.socket

import android.util.Log
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.DatagramPacket
import java.nio.charset.Charset

/**
 * Author: 海晨忆
 * Date: 2018/2/26
 * Desc:
 */
class UdpHandler : SimpleChannelInboundHandler<DatagramPacket>() {

    override fun messageReceived(ctx: ChannelHandlerContext?, msg: DatagramPacket?) {
        val byteBuf = msg!!.content()
        val bytes = ByteArray(byteBuf.readableBytes())
        byteBuf.readBytes(bytes)
        val s = String(bytes, Charset.forName("UTF-8"))
        Log.v("WZ", "UDP收到的消息是-->$s")
    }

}