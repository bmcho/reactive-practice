package com.bmcho.reacticestreampractice.socket.NIO;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class JavaNIOBlockingServer {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start JavaNIOBlockingServer");
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            serverSocket.bind(new InetSocketAddress("localhost", 8080));

            do {
                SocketChannel clientSocket = serverSocket.accept();

                ByteBuffer requestByteBuffer = ByteBuffer.allocateDirect(1024);
                int size = clientSocket.read(requestByteBuffer);
                requestByteBuffer.flip();
                String request = StandardCharsets.UTF_8.decode(requestByteBuffer).toString();
                log.info("from Client(SocketChannel) - size: {}, contents: {}, info: {}", size, request.trim(), clientSocket);

                ByteBuffer responeByteBuffer = ByteBuffer.wrap("Hello Client From NIO Server".getBytes());
                clientSocket.write(responeByteBuffer);
                clientSocket.close();
            } while (true);
        }
    }
}
