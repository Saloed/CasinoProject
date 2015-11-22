package frontend;

import authorizeClient.messages.MessageAuthorizeUser;
import authorizeClient.messages.MessageToAuthorizeClient;
import base.Abonent;
import base.Address;
import base.Message;
import base.MessageSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by FedoR on 16.11.2015.
 */
public class RegistrationController implements Initializable, Abonent{

    public TextField loginReg;
    public PasswordField passReg;
    private Main application;
    private MessageSystem messageSystem;
    private Boolean breaker = true;
private final Address address=new Address();

    public void setApp(Main application){
        this.application = application;
        messageSystem = application.getMessageSystem();
    }


    public void registration(ActionEvent event) {
        breaker = true;

        Message msg = new MessageAuthorizeUser(messageSystem.getAddressService().getFrontEndAddress(),
                messageSystem.getAddressService().getAuthorizeClientAddress(), loginReg.getText(), passReg.getText(), true);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void errorHappens(String error) {
        breaker = false;
      //  errorMessage.setText(error);

    }

    public void handleAnswer(Boolean answer) {
        breaker = false;

        if (answer) {
            application.gotoMainWin();
        } else {
          //  errorMessage.setText("Username/Password is incorrect");
        }
    }

    @Override
    public Address getAddress() {
        return address;
    }
}
