package com.sampleproject.chat_app.controller;

import com.sampleproject.chat_app.utill.Client;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;

public class ClientFormController {
    public VBox vboxMsg;
    public Label tblClient;
    public ScrollPane scrollPane;
    public static VBox senderVBox;
    public Client client;
    public static String userName="";
    public TextField txtMessageBox;

    public void initialize(){
        System.out.println("initialize");
    }

    public void setClientName(String name){
        userName=name;
        new Thread(()->{
            try {
                senderVBox = vboxMsg;
                tblClient.setText(name);
                client = new Client(new Socket("localhost",5000),name,vboxMsg);
                System.out.println("connected to the server");
                client.listenForMessage(vboxMsg,name);
                client.sendMessage(name+" has joined the chat!",vboxMsg,"SERVER");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
       /* vboxMsg.heightProperty().addListener((observable, oldValue, newValue) -> {
            scrollPane.setVvalue((Double)newValue);
        });*/
        vboxMsg.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane.setVvalue((Double) newValue);
            }
        });

    }


    public void exitClientOnClick(MouseEvent mouseEvent) {
    }
    public static void displayMessageOnRight(String messageToSend, VBox vBox){
        if (!messageToSend.isEmpty()){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5,5,5,10));
            Text msgText = new Text(messageToSend);
            TextFlow textFlow = new TextFlow(msgText);
            textFlow.setStyle("-fx-background-color: #1abc9c; -fx-background-radius: 10 10 0 10");
            textFlow.setPadding(new Insets(5,5,5,10));
            msgText.setFill(Color.WHITE);
            hBox.getChildren().add(textFlow);
            Platform.runLater(()->{
                vBox.getChildren().add(hBox);
            });
        }
    }

    public static void displayMessageOnLeft(String message, VBox vBox){
        if (!message.isEmpty()){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5,5,5,10));
            Text msgText = new Text(message);
            TextFlow textFlow = new TextFlow(msgText);
            textFlow.setStyle("-fx-background-color: #2ecc71; -fx-background-radius: 10 10 10 0");
            textFlow.setPadding(new Insets(5,10,5,10));
            msgText.setFill(Color.WHITE);
            hBox.getChildren().add(textFlow);
            Platform.runLater(()->{
                vBox.getChildren().add(hBox);
            });
        }
    }

    public void sendMessageOnClicked(MouseEvent mouseEvent) {
        client.sendMessage(txtMessageBox.getText(),vboxMsg,userName);
        txtMessageBox.clear();
    }
}
