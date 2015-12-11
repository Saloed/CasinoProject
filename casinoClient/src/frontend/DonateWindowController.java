package frontend;

import base.Message;
import base.MessageSystem;
import gameService.messages.MessageSendRequest;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by FedoR on 09.12.2015.
 */
public class DonateWindowController implements Initializable {


    public TextField moneyCount;
    private MessageSystem messageSystem;
    private MainWindowController application;

    public void setApp(MainWindowController application) {
        this.application = application;
        messageSystem = application.getMessageSystem();

    }

    public void sendMoney(ActionEvent event) {

        if ("".equals(moneyCount.getText()))
            return;

        Message msg = new MessageSendRequest(messageSystem.getAddressService().getMainWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), Integer.parseInt(moneyCount.getText()), null);
        messageSystem.sendMessage(msg);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
