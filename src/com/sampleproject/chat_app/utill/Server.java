package com.sampleproject.chat_app.utill;

import javafx.scene.layout.VBox;

import java.io.*;
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
    public void startServer(VBox serverVBox){
        try {
            socket = serverSocket.accept();
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
