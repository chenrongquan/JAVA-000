package io.github.outbound.nettyclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

import java.util.concurrent.*;

/**
 * @description: 作业二：netty-client客户端   仅支持同步(异步暂未实现)
 * @author: chenrq
 * @date: 2020年11月03日 17时42分
 */
public class NettyHttpClient {

    private CountDownLatch latch = new CountDownLatch(1);

    private EventLoopGroup group = new NioEventLoopGroup();

    /**
     * 服务端ip
     */
    private String hostIp = "127.0.0.1";
    /**
     * 服务端端口
     */
    private int port= 8088;
    /**
     * 通道
     */
    private SocketChannel socketChannel;

    /**
     * 初始化
     */
    private NettyClientHandlerInitilizer clientHandlerInitilizer;

    public void start() {
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        clientHandlerInitilizer = new NettyClientHandlerInitilizer(latch);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(hostIp, port)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(clientHandlerInitilizer);
        ChannelFuture channelFuture = bootstrap.connect();
        socketChannel = (SocketChannel) channelFuture.channel();
    }

    public void sendMsg(FullHttpRequest request, ChannelHandlerContext ctx) throws InterruptedException {
        socketChannel.writeAndFlush(request);
        latch.await(8, TimeUnit.SECONDS);
        FullHttpResponse response = clientHandlerInitilizer.getFullHttpResponse();
        if (request != null) {
            if (!HttpUtil.isKeepAlive(request)) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                ctx.write(response);
            }
        }
        ctx.flush();
    }


}