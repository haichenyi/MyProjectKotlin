package com.haichenyi.myproject.model.socket

import android.util.Log
import com.haichenyi.myproject.model.bean.SocketUdpBean
import io.netty.bootstrap.Bootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.Channel
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramPacket
import io.netty.channel.socket.nio.NioDatagramChannel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.net.InetSocketAddress
import java.nio.charset.Charset

/**
 * Author: 海晨忆
 * Date: 2018/2/26
 * Desc:
 */
class SocketUdp {
    companion object {
        private val socket = SocketUdp()
        fun getInstance(): SocketUdp = socket
    }

    private var port: Int = 0
    private var channel: Channel? = null
    private var group: EventLoopGroup? = null

    fun setPort(port: Int): SocketUdp {
        this.port = port
        return this
    }

    fun connect() {
        if (null != channel) return
        if (null == group) {
            if (!EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().register(this)
            group = NioEventLoopGroup()
        }
        val bootstrap = Bootstrap()
        bootstrap.group(group)
                .channel(NioDatagramChannel::class.java)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(UdpHandler())
        try {
            channel = bootstrap.bind(port).sync().channel()
            channel!!.closeFuture().sync()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            disConnect()
        }
    }

    /**
     * 断开tcp连接.
     */
    private fun disConnect() {
        if (null != group) {
            group!!.shutdownGracefully()
        }
//        EventBus.getDefault().unregister(this)
        group = null
        channel = null
        Log.v("WZ", "disConnect")
    }

    //连接成功后，通过Channel提供的接口进行IO操作
    private fun sendMessage(host: String, port: Int, data: ByteArray) {
        val packet = DatagramPacket(Unpooled.copiedBuffer(data),
                InetSocketAddress(host, port))
        channel?.let {
            try {
                it.writeAndFlush(packet).sync()
                Log.d("wz", "send succeed " + String(data, Charset.forName("UTF-8")))
            } catch (e: Exception) {
                reConnect()
                e.printStackTrace()
            }
        }
    }

    @Subscribe
    fun handle(socketUdpBean: SocketUdpBean) {
        sendMessage(socketUdpBean.host,socketUdpBean.port,socketUdpBean.data)
    }

    /**
     * 重连.
     */
    private fun reConnect() {
        Thread(Runnable { this.connect() }).start()
    }
}