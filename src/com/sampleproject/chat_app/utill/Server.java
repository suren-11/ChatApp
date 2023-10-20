package com.sampleproject.chat_app.utill;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private boolean ifShutdown;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.ifShutdown = false;
    }
}
