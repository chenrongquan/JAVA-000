package io.github.outbound.okhttp;

import io.github.outbound.httpclient4.NamedThreadFactory;
import io.github.router.HttpEndpointRouter;
import io.github.router.MyEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @description: 作业二：整合上次作业的 okhttp
 * @author: chenrq
 * @date: 2020年11月02日 11时49分
 */
public class OkhttpOutboundHandler {
    private List<String> backendUrls;
    private ExecutorService proxyService;
    private OkHttpClient httpClient;
    private HttpEndpointRouter router;

    public OkhttpOutboundHandler(String backendUrl) {
        String[] urls = backendUrl.split(";");
        backendUrls = new ArrayList<String>(urls.length);
        for (String url : urls) {
            url = url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
            backendUrls.add(url);
        }
        int cores = Runtime.getRuntime().availableProcessors() * 2;
        int keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        proxyService = new ThreadPoolExecutor(
                cores,
                cores,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"),
                handler
        );
        this.router = new MyEndpointRouter();
        this.httpClient = new OkHttpClient();
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        final String url = router.route(this.backendUrls) + fullRequest.uri();
        proxyService.submit(() -> fetchGet(fullRequest, ctx, url));
    }

    public void fetchGet(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final String url) {
        Request.Builder builder = new Request.Builder();
        fullRequest.headers().forEach(map -> {
            builder.addHeader(map.getKey(), map.getValue());
        });
        Request request = builder.url(url).build();
        try {
            Response response = httpClient.newCall(request).execute();
            handleResponse(fullRequest, ctx, response);
        } catch (IOException e) {
            e.printStackTrace();
            exceptionCaught(ctx, e);
        }

    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final Response endpointResponse) {
        FullHttpResponse response = null;
        try {
            byte[] body = endpointResponse.body().bytes();
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.header("Content-Length")));
        } catch (IOException e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        }finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}