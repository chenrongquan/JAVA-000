package io.github.outbound.nettyclient;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.concurrent.CountDownLatch;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月03日 18时24分
 */
public class NettyClientHandler  extends ChannelInboundHandlerAdapter {

    private CountDownLatch latch;

    private FullHttpResponse fullHttpResponse;

    public NettyClientHandler(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        latch.countDown();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            String result = content.content().toString(CharsetUtil.UTF_8);
            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(result.getBytes()));
            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().set("Content-Length", result.length());
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
        cause.printStackTrace();
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }

    public FullHttpResponse getFullHttpResponse() {
        return fullHttpResponse;
    }
}



