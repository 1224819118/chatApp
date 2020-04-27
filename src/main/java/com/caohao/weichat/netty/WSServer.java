package com.caohao.weichat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Component;

@Component
public class WSServer {

    private static class SingletionWSServer{
        static final WSServer instance = new WSServer();
    }

    public static WSServer getInstance(){
        return SingletionWSServer.instance;
    }

    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private ServerBootstrap serverBootstrap;
    private ChannelFuture channelFuture;

    public  WSServer(){
        //创建线程组
        bossGroup = new NioEventLoopGroup();
        workGroup = new NioEventLoopGroup();
        //创建系统的启动器
        serverBootstrap = new ServerBootstrap();
        //注册系统的一些基本信息
        serverBootstrap.group(bossGroup,workGroup)//注册两个主从线程组
                .channel(NioServerSocketChannel.class)//设定管道类型是nio
                .childHandler(new WSServerInitialzer());//注册初始化器，这个初始化器会将所有的处理器集中起来
    }

    public void start(){
        this.channelFuture = serverBootstrap.bind(8088);
        System.err.println("netty websocket server 启动成功...");
    }
//
//    public static void main(String[] args) throws InterruptedException {
//        //创建线程组
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup workGroup = new NioEventLoopGroup();
//
//        try {
//            //创建系统的启动器
//            ServerBootstrap serverBootstrap = new ServerBootstrap();
//            //注册系统的一些基本信息
//            serverBootstrap.group(bossGroup,workGroup)//注册两个主从线程组
//                    .channel(NioServerSocketChannel.class)//设定管道类型是nio
//                    .childHandler(null);//注册初始化器，这个初始化器会将所有的处理器集中起来
//            //绑定监听的端口
//            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
//            //关闭
//            channelFuture.channel().closeFuture().sync();
//        } finally {
//            //关闭
//            bossGroup.shutdownGracefully();
//            workGroup.shutdownGracefully();
//        }
//
//
//    }
}
