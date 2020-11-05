package io.github.outbound.nettyclient;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月05日 10时10分
 */
public class NettyClientHandlerInitilizer extends ChannelInitializer<SocketChannel> {

    private NettyClientHandler clientHandler;

    private CountDownLatch latch;

    public NettyClientHandlerInitilizer(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        clientHandler = new NettyClientHandler(latch);
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast(new HttpResponseDecoder());
        channelPipeline.addLast(new HttpRequestEncoder());
        channelPipeline.addLast(clientHandler);
    }

    public FullHttpResponse getFullHttpResponse() {
        return clientHandler.getFullHttpResponse();
    }

}