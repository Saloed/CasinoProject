package frontend;


import authorizeClient.messages.MessageAuthorizeUser;
import base.Abonent;
import base.Address;
import base.Message;
import base.MessageSystem;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthorizeController implements Initializable, Abonent {

    private final Address address = new Address();
    public TextField login;
    public PasswordField pass;
    public Label errorMessage;
    public Button sign;
    private Main application;
    private MessageSystem messageSystem;
    private Boolean breaker = true;

    public void setApp(Main application) {
        this.application = application;
        messageSystem = application.getMessageSystem();
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
    }


    public Address getAddress() {
        return address;
    }

    public void checkLogPass(ActionEvent actionEvent) throws IOException {

        breaker = true;

        Message msg = new MessageAuthorizeUser(messageSystem.getAddressService().getFrontEndAddress()
                , messageSystem.getAddressService().getAuthorizeClientAddress(), login.getText(), pass.getText(), false);
        messageSystem.sendMessage(msg);

        while (breaker) {
            messageSystem.execForAbonent(this);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public void handleAnswer(Boolean answer) {
        breaker = false;

        if (answer) {
            application.gotoMainWin();
        } else {
            errorMessage.setText("Username/Password is incorrect");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        errorMessage.setText("");
        login.setPromptText("Login");
        pass.setPromptText("Password");

    }

    public void gotoReg(ActionEvent event) {
        application.gotoReg();
    }
}

