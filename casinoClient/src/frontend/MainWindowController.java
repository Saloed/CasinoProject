package frontend;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by FedoR on 16.11.2015.
 */
public class MainWindowController implements Initializable {

    public TextField messageTextField;
    public Label userName;
    public Label cash;
    private Main application;

    public void setApp(Main application){this.application = application;}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userName.setText("Fedor");//Get his name and put on main win lbl
        messageTextField.setPromptText("Enter message...");
        cash.setText("1 000 000$"); //$$$

    }
}
