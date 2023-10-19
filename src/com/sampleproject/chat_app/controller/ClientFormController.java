package com.sampleproject.chat_app.controller;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ClientFormController {
    String clientName;

    public void initialize(){
        System.out.println("initialize");
    }

    public void setClientName(String name){
        this.clientName=name;
        System.out.println(clientName);
    }
    public TextField txtMessageBox;

    public void exitClientOnClick(MouseEvent mouseEvent) {
    }
}
