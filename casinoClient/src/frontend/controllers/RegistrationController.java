package frontend.controllers;

import authorizeClient.messages.MessageAuthorizeUser;
import base.Abonent;
import base.Address;
import base.Message;
import base.MessageSystem;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable, Abonent {

    private final Address address = new Address();
    public TextField loginReg;
    public PasswordField passReg;
    private Main application;
    private MessageSystem messageSystem;

    public void setApp(Main application) {
        this.application = application;
        messageSystem = application.getMessageSystem();
    }


    public void registration(ActionEvent event) {

        if ("".equals(loginReg.getText()) || "".equals(passReg.getText())) {
            return;
        }

        Message msg = new MessageAuthorizeUser(messageSystem.getAddressService().getAuthorizeControllerAddress(),
                messageSystem.getAddressService().getAuthorizeClientAddress(), loginReg.getText(), passReg.getText(), true);
        messageSystem.sendMessage(msg);


        application.gotoLogin();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public Address getAddress() {
        return address;
    }
}
