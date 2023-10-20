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
            while (!ifShutdown){
                socket = serverSocket.accept();
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ClientHandler clientHandler = new ClientHandler(socket, serverVBox);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeServerSocket(){
        ifShutdown=true;
        try {
            if (serverSocket!=null) serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
