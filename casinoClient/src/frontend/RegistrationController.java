package frontend;

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
public class RegistrationController implements Initializable{

    public TextField loginReg;
    public PasswordField passReg;
    private Main application;

    public void setApp(Main application){this.application = application;}


    public void registration(ActionEvent event) {

        String lu = loginReg.getText();
        String pu = passReg.getText();

        /*application.verify(lu,pu,true);*/
        application.gotoLogin();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
