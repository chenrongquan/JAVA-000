package io.github.outbound.nettyclient;

import io.github.outbound.IHttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月06日 01时25分
 */
public class NettyHttpOutboundHandler implements IHttpOutboundHandler {

    private NettyHttpClient client;

    public NettyHttpOutboundHandler() {
        client = new NettyHttpClient();
        client.start();
    }

    @Override
    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) throws InterruptedException {
        client.sendMsg(fullRequest, ctx);
    }
}