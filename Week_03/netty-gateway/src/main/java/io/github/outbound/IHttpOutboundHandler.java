package io.github.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月06日 01时44分
 */
public interface IHttpOutboundHandler {
    void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) throws InterruptedException;
}
