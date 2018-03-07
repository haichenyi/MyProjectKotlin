package com.haichenyi.myproject.model.socket

import android.os.SystemClock
import android.util.Log
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import java.util.concurrent.TimeUnit


class HeartTcp : SimpleChannelInboundHandler<Any>() {
    private var ctx: ChannelHandlerContext? = null
    private var isConnect = false

    @Throws(Exception::class)
    override fun channelActive(ctx: ChannelHandlerContext) {
        super.channelActive(ctx)
        Log.v("WZ", "连接正常channelActive")
        isConnect = true
        if (this.ctx == null) {
            synchronized(HeartTcp::class.java) {
                if (this.ctx == null) {
                    this.ctx = ctx
                    myAppHeart()
                }
            }
        }
    }

    private fun myAppHeart() {
        Thread {
            while (ctx != null && isConnect) {
                val data = "123"
                val bytes = data.toByteArray()
                if (isConnect) {
                    ctx!!.writeAndFlush(Unpooled.buffer(bytes.size).writeBytes(bytes))
                    SystemClock.sleep(3000)
                }
            }
        }.start()
    }

    @Throws(Exception::class)
    override fun channelInactive(ctx: ChannelHandlerContext) {
        val loop = ctx.channel().eventLoop()
        loop.schedule({ SocketTcp.getInstance().connect() }, 5, TimeUnit.SECONDS)
        super.channelInactive(ctx)
        Log.v("WZ", "重新连接socket服务器")
        isConnect = false
    }

    @Throws(Exception::class)
    override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
        super.userEventTriggered(ctx, evt)
        Log.v("WZ", "发送数据包")
    }

    @Throws(Exception::class)
    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        super.exceptionCaught(ctx, cause)
        Log.v("WZ", "连接出现异常")
        this.ctx = null
    }

    @Throws(Exception::class)
    override fun messageReceived(ctx: ChannelHandlerContext, msg: Any) {
        Log.v("WZ", "连接正常messageReceived")
        /*val msg1 = msg
        val bytes = ByteArray(msg1.readableBytes())
        msg1.readBytes(bytes)
        val s = String(bytes*//*, "UTF-8"*//*)*/
        val s = msg.toString()
        Log.v("WZ", "接收到的消息:" + s)
    }
}
