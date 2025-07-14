package com.bmcho.reactivepractice.socket.IO;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class JavaIOOnceServer {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start JavaIOOnceServer");
        try (ServerSocket serverSocket = new ServerSocket()) {

            serverSocket.bind(new InetSocketAddress("localhost", 8080));
            Socket clientSocket = serverSocket.accept();

            byte[] request = new byte[1024];
            InputStream in = clientSocket.getInputStream();
            int size = in.read(request);
            log.info("to Client - size: {}, contents: {}", size, new String(request).trim());

            OutputStream out = clientSocket.getOutputStream();
            String response = "Hello Client";
            out.write(response.getBytes());
            out.flush();
        }
        log.info("end JavaIOOnceServer");
    }
}
