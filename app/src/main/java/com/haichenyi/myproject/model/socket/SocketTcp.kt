package com.haichenyi.myproject.model.socket

import android.util.Log
import com.haichenyi.myproject.model.bean.SocketTcpBean
import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.LineBasedFrameDecoder
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.util.CharsetUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


/**
 * Author: 海晨忆
 * Date: 2018/2/26
 * Desc:
 */
class SocketTcp {
    companion object {
        private val socket = SocketTcp()
        fun getInstance(): SocketTcp = socket
    }

    private var port: Int = 0
    private var host: String = ""
    private var channel: Channel? = null
    private var group: EventLoopGroup? = null
    fun setPort(port: Int): SocketTcp {
        this.port = port
        return this
    }

    fun setHost(host: String): SocketTcp {
        this.host = host
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
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel::class.java)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(object : ChannelInitializer<SocketChannel>() {
                        override fun initChannel(ch: SocketChannel?) {
                            //以换行符为结束标记
                            val pipeline = ch!!.pipeline()
                            pipeline.addLast("encoder", StringEncoder(CharsetUtil.UTF_8))
                            pipeline.addLast(LineBasedFrameDecoder(Integer.MAX_VALUE))
                            pipeline.addLast(StringDecoder())
                            pipeline.addLast(HeartTcp())

                            //以"#_"作为分隔符
                            /*val pipeline = ch!!.pipeline()
                            pipeline.addLast("encoder", StringEncoder(CharsetUtil.UTF_8))
                            val s = "#_"
                            val byteBuf = Unpooled.copiedBuffer(s.toByteArray())
                            pipeline.addLast(DelimiterBasedFrameDecoder(Integer.MAX_VALUE, byteBuf))
                            pipeline.addLast(StringDecoder())*/
                        }
                    })
            //发起异步连接操作
            val channelFuture = bootstrap.connect(host, port).sync()
            channel = channelFuture.channel()
            //等待服务端监听端口关闭
            channel!!.closeFuture().sync()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            disConnect()
        }
    }

    //连接成功后，通过Channel提供的接口进行IO操作
    private fun sendMessage(msg: String) {
        try {
            if (channel != null && channel!!.isOpen) {
                channel!!.writeAndFlush(msg).sync()
                Log.d("wz", "send succeed " + msg)
            } else {
                throw Exception("channel is null | closed")
            }
        } catch (e: Exception) {
            reConnect()
            e.printStackTrace()
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

    @Subscribe
    fun handle(socketTcpBean: SocketTcpBean) {
        sendMessage(socketTcpBean.msg)
    }

    /**
     * 重连.
     */
    private fun reConnect() {
        Thread(Runnable { this.connect() }).start()
    }
}