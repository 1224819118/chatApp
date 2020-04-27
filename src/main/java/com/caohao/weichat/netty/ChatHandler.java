package com.caohao.weichat.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * 处理消息的handler
 * TextWebSocketFrame：用于为websocket专门处理文本的对象frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //记录所有客户端的channel
    private static ChannelGroup usersChannel = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame message) throws Exception {
        String text = message.text();
        System.out.println("接收到的消息"+text);
        Channel channel = channelHandlerContext.channel();
//        for (Channel channel1:usersChannel){
//            channel1.writeAndFlush(new TextWebSocketFrame("[服务器接收到消息：]"+ LocalDateTime.now()
//            +"消息为："+text));
//        }
        usersChannel.writeAndFlush(new TextWebSocketFrame("[服务器接收到消息：]"+ LocalDateTime.now()
                +"消息为："+text));
    }

    /**
     * 当客户端打开链接就将这个channel放入管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        usersChannel.add(channel);
    }

    /**
     * 关闭浏览器时移除相应的channel
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //这句话不写也自动移除
        //usersChannel.remove(ctx.channel());
        System.out.println("移除了id为"+ctx.channel().id().asShortText()+"channel");
    }
}
