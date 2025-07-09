package com.bmcho.reacticestreampractice.socket.IO;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class JavaIOServer {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start JavaIOServer");
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress("localhost", 8080));

            do {
                Socket clientSocket = serverSocket.accept();

                byte[] request = new byte[1024];
                InputStream in = clientSocket.getInputStream();
                int size = in.read(request);
                log.info("from Client - size: {}, contents: {}, info: {}", size, new String(request).trim(), clientSocket);

                OutputStream out = clientSocket.getOutputStream();
                String response = "Hello Client";
                out.write(response.getBytes());
                out.flush();
            } while (true);
        }
    }
}
