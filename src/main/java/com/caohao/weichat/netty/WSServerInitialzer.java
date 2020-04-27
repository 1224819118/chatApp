package com.caohao.weichat.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //加入http编解码器
        pipeline.addLast(new HttpServerCodec());
        //http聚合器对HTTPmessage进行聚合聚合为FULLHTTPREAUEST/REPONSE
        // 基本在netty的编程中都会使用到这个
        pipeline.addLast(new HttpObjectAggregator(1024*64));
        // =================以上是HTTP协议的支持===========
        //针对websocket处理的的协议，用与指定给客户端连接访问的路由
        //会帮你处理一些握手动作（close，ping，pong）等繁重且复杂的事
        //对于websocket来讲都是以frames来传输的
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //加入自定义助手类
        pipeline.addLast(new ChatHandler());

    }
}
