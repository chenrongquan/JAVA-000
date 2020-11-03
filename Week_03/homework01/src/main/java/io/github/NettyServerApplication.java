package io.github;

import io.github.inbound.HttpInboundServer;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月02日 10时18分
 */
public class NettyServerApplication {

    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";

    public static void main(String[] args) {
        String proxyServer = System.getProperty("proxyServer","http://localhost:8088;http://localhost:8089");
        String proxyPort = System.getProperty("proxyPort","8888");

        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" starting...");
        HttpInboundServer server = new HttpInboundServer(port, proxyServer);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" started at http://localhost:" + port + " for server:" + proxyServer);
        try {
            server.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}