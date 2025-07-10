package com.bmcho.reacticestreampractice.reactor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class HttpEventHandler implements EventHandler {
    private final ExecutorService executorService = Executors.newFixedThreadPool(50);
    private final SocketChannel clientSocket;
    private final MsgCodec msgCodec;

    @SneakyThrows
    public HttpEventHandler(Selector selector, SocketChannel clientSocket) {
        this.clientSocket = clientSocket;
        this.clientSocket.configureBlocking(false);
        this.clientSocket.register(selector, SelectionKey.OP_READ).attach(this);
        this.msgCodec = new MsgCodec();
    }

    @Override
    public void handle() {
        String requestBody = handleRequest();
        log.info("requestBody: {}", requestBody);
        sendResponse(requestBody);
    }

    @SneakyThrows
    private String handleRequest() {
        ByteBuffer requestByteBuffer = ByteBuffer.allocateDirect(1024);
        this.clientSocket.read(requestByteBuffer);
        return msgCodec.decode(requestByteBuffer);
    }

    @SneakyThrows
    private void sendResponse(String requestBody) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10);

                ByteBuffer responeByteBuffer = msgCodec.encode(requestBody);
                this.clientSocket.write(responeByteBuffer);
                this.clientSocket.close();
            } catch (Exception e) { }
        }, executorService);
    }
}
