package frontend.controllers;

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
    private List<Integer> betPlace;

    public void setApp(RouletteWindowController application, List<Integer> betPlace) {
        this.application = application;
        this.betPlace = betPlace;
        messageSystem = application.getMessageSystem();

    }


    public void sendBet(ActionEvent event) {

        String bid = "You bet " + betCount.getText() + " on " + betPlace;
        application.collectBid(bid);
        int cash = Integer.parseInt(betCount.getText());
        GameMessage.Request.ServerRequest.Bet bet = GameMessage.Request.ServerRequest.Bet.newBuilder()
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
