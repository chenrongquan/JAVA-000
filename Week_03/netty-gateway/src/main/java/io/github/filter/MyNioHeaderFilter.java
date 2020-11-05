package io.github.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @description: 作业三：请求头过滤器
 * @author: chenrq
 * @date: 2020年11月03日 11时11分
 */
public class MyNioHeaderFilter implements HttpRequestFilter{
    public static final String NIO_KEY = "nio";
    public static final String NIO_VALUE = "chenrq";
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().add(NIO_KEY, NIO_VALUE);
    }
}