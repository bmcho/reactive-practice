package com.bmcho.reactivepractice.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@Slf4j
public class NettyEchoServerExample {

    private static ChannelInboundHandler echoHandler() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                if (msg instanceof ByteBuf) {
                    try {
                        var buf = (ByteBuf) msg;
                        var len = buf.readableBytes();
                        var charset = StandardCharsets.UTF_8;
                        var body = buf.readCharSequence(len, charset);
                        log.info("EchoHandler.channelRead: " + body);

                        buf.readerIndex(0); // flip
                        var result = buf.copy();
                        ctx.writeAndFlush(result)
                            .addListener(ChannelFutureListener.CLOSE);
                    } finally {
                        ReferenceCountUtil.release(msg);
                    }
                }
            }
        };
    }

    private static ChannelHandler acceptor(EventLoopGroup childGroup) {
        var executorGroup = new DefaultEventExecutorGroup(4);

        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.info("Acceptor.channelRead");
                if (msg instanceof SocketChannel socketChannel) {
                    socketChannel.pipeline().addLast(
                        executorGroup, new LoggingHandler(LogLevel.INFO));
                    socketChannel.pipeline().addLast(
                        echoHandler()
                    );
                    childGroup.register(socketChannel);
                }
            }
        };
    }

    public static void main(String[] args) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup(4);

        NioServerSocketChannel serverSocketChannel = new NioServerSocketChannel();
        parentGroup.register(serverSocketChannel);
        serverSocketChannel.pipeline().addLast(acceptor(childGroup));

        serverSocketChannel.bind(new InetSocketAddress(8080))
            .addListener(future -> {
                if (future.isSuccess()) {
                    log.info("Server bound to port 8080");
                }
            });
    }
}
