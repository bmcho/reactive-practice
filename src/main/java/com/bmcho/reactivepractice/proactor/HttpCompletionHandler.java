package com.bmcho.reactivepractice.proactor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

@Slf4j
public class HttpCompletionHandler implements CompletionHandler<Integer, Void> {

    private final MsgCodec msgCodec;
    private final ByteBuffer byteBuffer;
    private final AsynchronousSocketChannel socketChannel;

    public HttpCompletionHandler(AsynchronousSocketChannel socketChannel) {
        msgCodec = new MsgCodec();
        byteBuffer = ByteBuffer.allocateDirect(1024);
        this.socketChannel = socketChannel;
        this.socketChannel.read(this.byteBuffer, null, this);
    }

    @Override
    public void completed(Integer result, Void attachment) {
        String requestBody = handleRequest();
        log.info("requestBody: {}", requestBody);
        sendResponse(requestBody);
    }


    @Override
    public void failed(Throwable exc, Void attachment) {
        log.error("Failed to read from client", exc);
    }

    private String handleRequest() {
        return msgCodec.decode(byteBuffer);
    }

    @SneakyThrows
    private void sendResponse(String requestBody) {
        ByteBuffer responseByteBuffer = msgCodec.encode(requestBody);
        socketChannel.write(responseByteBuffer, null, new CompletionHandler<>() {
            @SneakyThrows
            @Override
            public void completed(Integer result, Object attachment) {
                log.info("sendResponse completed");
                socketChannel.close();
            }

            @SneakyThrows
            @Override
            public void failed(Throwable exc, Object attachment) {
                log.error("sendResponse error", exc);
                socketChannel.close();
            }
        });
    }

}
