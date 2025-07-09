package com.bmcho.reacticestreampractice.socket.IO;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

@Slf4j
public class JavaIOClient {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start JavaIOClient");

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", 8080));

            OutputStream out = socket.getOutputStream();
            String request = "Hello Server";
            out.write(request.getBytes());
            out.flush();

            InputStream in = socket.getInputStream();
            byte[] response = new byte[1024];
            int size = in.read(response);
            log.info("from Server - size: {}, contents: {}", size, new String(response).trim());
        }

        log.info("end JavaIOClient");
    }
}
