package frontend.controllers;

import base.Abonent;
import base.Address;
import base.Message;
import base.MessageSystem;
import frontend.WorkThread;
import gameService.messages.MessageSendMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainWindowController implements Initializable, Abonent {

    private final Address address = new Address();
    private final ObservableList<String> chatWindowData = FXCollections.observableArrayList();
    private final ExecutorService worker = Executors.newSingleThreadExecutor();
    public TextField messageTextField;
    public Label userName;
    public Label cash;
    public ListView<String> chatWindow;
    public ImageView rouletteBtn;
    public ImageView slotBtn;
    private Main application;
    private MessageSystem messageSystem;
    private int moneyCount;

    public void setApp(Main application) {
        this.application = application;
        messageSystem = application.getMessageSystem();
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
        application.startServices();
        userName.setText(application.getUserName());
        moneyCount = application.getCash();
        cash.setText("" + moneyCount);
        worker.execute(new WorkThread(messageSystem, this, "Main Window"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        messageTextField.setPromptText("Enter message...");
        cash.setText(Integer.toString(moneyCount)); //$$$
        rouletteBtn.setImage(new Image("/frontend/res/roulette.png"));
        rouletteBtn.setStyle("-fx-background-color: transparent");
        slotBtn.setImage(new Image("/frontend/res/slot-machine.png"));


    }

    public Address getAddress() {
        return address;
    }


    public void sendMessage(ActionEvent event) {
        Message msg = new MessageSendMessage(messageSystem.getAddressService().getMainWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), messageTextField.getText());
        messageSystem.sendMessage(msg);

        messageTextField.clear();
    }

    public void getMessage(String message) {

        Platform.runLater(new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                chatWindowData.add(message);
                chatWindow.setItems(chatWindowData);

                return null;
            }
        });

    }

    public void stopController() {
        worker.shutdown();
        try {
            worker.awaitTermination(100, TimeUnit.MILLISECONDS);
            worker.shutdownNow();
        } catch (InterruptedException e) {
            System.err.println("Error when shuttdown MainWindow Controller");
        }
    }

    public void goToSlotWindow(Event event) {
        application.gotoSlotWin();
    }


    public void goToRouletteWindow(Event event) {
        application.gotoRouletteWin();
    }

    public void startDonateWindow(ActionEvent event) throws Exception {

        DonateWindowController donate = (DonateWindowController) application.replaceSceneContent("/frontend/FXML/DonateWindow.fxml", event, "$$$");
        donate.setApp(this);

    }

    public void updateCash(int cash) {
        application.takeCash(application.getCash() + cash);
        moneyCount = application.getCash();
        this.cash.setText("" + moneyCount);
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }


}
