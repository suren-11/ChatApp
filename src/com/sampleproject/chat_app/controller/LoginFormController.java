package com.sampleproject.chat_app.controller;

import com.gluonhq.charm.glisten.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public TextField txtUserName;
    public AnchorPane loginFormContext;

    public void startChatOnAction(ActionEvent actionEvent) throws IOException {
        if (!txtUserName.getText().trim().isEmpty()){
            /*Stage stage = new Stage();
            stage.setScene(new Scene(
                    FXMLLoader.load(
                            getClass().getResource("../view/ClientForm.fxml")
                    )
            ));
            stage.setTitle(txtUserName.getText());
            Stage st = (Stage) loginFormContext.getScene().getWindow();
            st.close();*/
            Stage st = (Stage) loginFormContext.getScene().getWindow();
            st.setTitle(txtUserName.getText());
            st.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ClientForm.fxml"))));
        }else {
            new Alert(Alert.AlertType.WARNING,"User name is required!").show();
        }
    }
}
