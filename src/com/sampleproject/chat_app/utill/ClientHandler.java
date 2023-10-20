package com.sampleproject.chat_app.utill;

import com.sampleproject.chat_app.controller.ServerFormController;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> allClients = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;

    private VBox vBox;
    public  String userName;

    public ClientHandler(Socket socket, VBox vBox){
        try {
            this.socket=socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.printWriter = new PrintWriter(socket.getOutputStream(),true);
            this.userName = bufferedReader.readLine();
            this.vBox = vBox;
            allClients.add(this);
        }catch (IOException e){
            closeAll(this.socket,this.bufferedReader,this.bufferedWriter);
        }
    }
    public void closeAll(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if (bufferedReader!=null) bufferedReader.close();
            if (bufferedWriter!=null) bufferedWriter.close();
            if (socket!=null) socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void run() {
        ServerFormController.displayMessageOnLeft(userName+" has joined the chat",vBox);
        String msgFromClient;
        while (socket.isConnected()){
            try {
                msgFromClient = bufferedReader.readLine();
                if (msgFromClient.contains("left")){
                    removeFromTheChat();
                }
                broadcastMessage(msgFromClient);
            }catch (IOException e){
                closeAll(this.socket,this.bufferedReader,this.bufferedWriter);
            }
        }
    }

    public void broadcastMessage(String msgBroadcast) {
        for (ClientHandler clients: allClients){
            try {
                if (!clients.userName.equals(userName)){
                    clients.bufferedWriter.write(msgBroadcast);
                    clients.bufferedWriter.newLine();
                    clients.bufferedWriter.flush();
                    System.out.println("");
                }
                if (clients.userName.equals(userName)){
                    String[] originalMsg = msgBroadcast.split(":");
                    if (originalMsg.length==2){
                        sendToOriginalUser(clients,originalMsg[1]);
                    }
                }
            }catch (Exception e){
                closeAll(this.socket,this.bufferedReader,this.bufferedWriter);
            }
        }
    }

    private void sendToOriginalUser(ClientHandler clients, String originalMessage) {
        try {
            clients.bufferedWriter.write("Sender :"+originalMessage);
            clients.bufferedWriter.newLine();
            clients.bufferedWriter.flush();
            System.out.println("..");
        }catch (Exception e){
            closeAll(this.socket,this.bufferedReader,this.bufferedWriter);
        }
    }

    public void removeFromTheChat(){
        allClients.remove(this);
        ServerFormController.displayMessageOnLeft(this.userName+" has left the chat",vBox);
        closeAll(this.socket,this.bufferedReader,this.bufferedWriter);
    }
}
