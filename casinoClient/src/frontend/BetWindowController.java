package frontend;

import base.GameMessage;
import base.MessageSystem;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by FedoR on 17.12.2015.
 */
public class BetWindowController implements Initializable {


    public TextField betCount;
    public Button betBrn;
    private MessageSystem messageSystem;
    private RouletteWindowController application;
    //private String betPlace;
    private List<Integer> betPlace;

    public void setApp(RouletteWindowController application, List<Integer> betPlace) {
        this.application = application;
        this.betPlace = betPlace;
        messageSystem = application.getMessageSystem();

    }


    public void sendBet(ActionEvent event) {

        System.out.println("Your bet is:" + betPlace);
        /*
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 35, Integer.parseInt(betCount.toString()));
        messageSystem.sendMessage(msg);*/
        int cash = Integer.parseInt(betCount.getText());
        GameMessage.ServerRequest.Bet bet = GameMessage.ServerRequest.Bet.newBuilder()
                .setCash(cash)
                .setCoefficient(36 / betPlace.size())
                .addAllBet(betPlace)
                .build();
        application.acceptBet(bet);
        ((Stage) (betBrn.getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
