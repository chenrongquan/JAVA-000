package io.github.inbound;

import io.github.filter.HttpRequestFilter;
import io.github.filter.MyNioHeaderFilter;
import io.github.outbound.IHttpOutboundHandler;
import io.github.outbound.nettyclient.NettyHttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月02日 10时47分
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final String proxyServer;
    private IHttpOutboundHandler handler;
    private HttpRequestFilter filter;

    public HttpInboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
//        this.handler = new OkhttpOutboundHandler(this.proxyServer);  // OkHttp
//        this.handler = new HttpOutboundHandler(this.proxyServer);
        this.handler = new NettyHttpOutboundHandler();    // Netty Client
        this.filter = new MyNioHeaderFilter();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)  {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            // 过滤器处理
            filter.filter(fullRequest, ctx);
            handler.handle(fullRequest, ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }
}