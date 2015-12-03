package frontend;

import base.Abonent;
import base.Address;
import base.Message;
import base.MessageSystem;
import gameService.GameService;
import gameService.messages.MessageChangeCurrentGame;
import gameService.messages.MessageSendRequest;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by FedoR on 01.12.2015.
 */
public class RouletteWindowController implements Initializable, Abonent {
    public Button r1;
    public Button b2;
    public Button r3;
    public Button b4;
    public Button r5;
    public Button b6;
    public Button r7;
    public Button b8;
    public Button r9;
    public Button b10;
    public Button b11;
    public Button r12;
    public Button b13;
    public Button r14;
    public Button b15;
    public Button r16;
    public Button b17;
    public Button r18;
    public Button r19;
    public Button b20;
    public Button r21;
    public Button b22;
    public Button r23;
    public Button b24;
    public Button r25;
    public Button b26;
    public Button r27;
    public Button b28;
    public Button b29;
    public Button r30;
    public Button b31;
    public Button r32;
    public Button b33;
    public Button r34;
    public Button b35;
    public Button r36;


    public Label cashField;


    private Main application;
    private MessageSystem messageSystem;
    private final Address address = new Address();
    private final ExecutorService worker = Executors.newSingleThreadExecutor();
    private int[] array = new int[36];


    public void setApp(Main application) {
        this.application = application;
        messageSystem = application.getMessageSystem();
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
        worker.execute(new WorkThread(messageSystem, this, "Roulette"));

        GameService.GameType newGame = GameService.GameType.ROULETTE;
        Message msg = new MessageChangeCurrentGame(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), newGame);
        messageSystem.sendMessage(msg);
        cashField.setText("" + application.getCash());

    }

    public void startRoulette(ActionEvent event) {

        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 7, 100);
        messageSystem.sendMessage(msg);


    }

    public void setResult(int resultCash, int result) {
        Platform.runLater(new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                cashField.setText(Integer.toString(resultCash));
                System.out.println(result);

                return null;
            }
        });

    }

    public void goBack(ActionEvent event) {
        application.gotoMainWin();
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getRouletteWindowControllerAddress()
                , messageSystem.getAddressService().getGameServiceAddress(), null, null);
        messageSystem.sendMessage(msg);
        stopController();
    }

    public void stopController() {
        worker.shutdown();
        try {
            worker.awaitTermination(15, TimeUnit.MILLISECONDS);
            worker.shutdownNow();
        } catch (InterruptedException e) {
            System.err.println("Error when shuttdown roulette Controller");
        }
    }


    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void bet_r36(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 36, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b35(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 35, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r34(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 34, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b33(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 33, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r32(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 32, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b31(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 31, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r30(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 30, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b29(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 29, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b28(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 28, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r27(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 27, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b26(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 26, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r25(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 25, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b24(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 24, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r23(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 23, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b22(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 22, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r21(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 21, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b20(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 20, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r19(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 19, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r18(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 18, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b17(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 17, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r16(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 16, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b15(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 15, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r14(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 14, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b13(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 13, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r12(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 12, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b11(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 11, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b10(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 10, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r9(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 9, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b8(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 8, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r7(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 7, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b6(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 6, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r5(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 5, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b4(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 4, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r3(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 3, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_b2(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 2, 100);
        messageSystem.sendMessage(msg);

    }

    public void bet_r1(ActionEvent event) {
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), 1, 100);
        messageSystem.sendMessage(msg);

    }
}
