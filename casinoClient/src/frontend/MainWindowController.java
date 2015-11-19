package frontend;

import base.Abonent;
import base.Address;
import base.Message;
import base.MessageSystem;
import chatClient.messages.MessageSendMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.Main;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by FedoR on 16.11.2015.
 */
public class MainWindowController implements Initializable, Abonent {

    private final Address address = new Address();
    public TextField messageTextField;
    public Label userName;
    public Label cash;
    public ListView chatWindow;
    private Main application;
    private MessageSystem messageSystem;
    private Boolean breaker = true;
    private ObservableList<String> chatWindowData = FXCollections.observableArrayList();

    public void setApp(Main application){
        this.application = application;
        messageSystem = application.getMessageSystem();
        userName.setText(application.getLogin());
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
      //TODO remake
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

        messageTextField.setPromptText("Enter message...");
        cash.setText("1 000 000$"); //$$$


    }

    public Address getAddress() {
        return address;
    }

    public void sendMessage(ActionEvent event) {
breaker=true;
        Message msg = new MessageSendMessage(messageSystem.getAddressService().getMainWindowControllerAdress(),
                messageSystem.getAddressService().getChatClientAddress(),messageTextField.getText());
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

    public void getMessage(String message)
    {
        breaker=false;
        chatWindowData.add(message);
        chatWindow.setItems(chatWindowData);
    }
}
