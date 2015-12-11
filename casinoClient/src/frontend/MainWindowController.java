package frontend;

import base.Abonent;
import base.Address;
import base.Message;
import base.MessageSystem;
import chatClient.messages.MessageSendMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by FedoR on 16.11.2015.
 */
public class MainWindowController implements Initializable, Abonent {

    private final Address address = new Address();
    public TextField messageTextField;
    public Label userName;
    public Label cash;
    public ListView<String> chatWindow;
    private Main application;
    private MessageSystem messageSystem;
    private int moneyCount;
    private final ObservableList<String> chatWindowData = FXCollections.observableArrayList();
    private final ExecutorService worker = Executors.newSingleThreadExecutor();


    public void setApp(Main application) {
        this.application = application;
        messageSystem = application.getMessageSystem();
        userName.setText(application.getUserName());
        moneyCount = application.getCash();
        cash.setText("" + moneyCount);
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
        worker.execute(new WorkThread(messageSystem, this, "Main Window"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        messageTextField.setPromptText("Enter message...");
        cash.setText(Integer.toString(moneyCount)); //$$$


    }

    public Address getAddress() {
        return address;
    }

    //TODO add payment system
    //for update cash send MessageSendRequest (from geme service package)
    //set money as betCash

    public void sendMessage(ActionEvent event) {
        Message msg = new MessageSendMessage(messageSystem.getAddressService().getMainWindowControllerAddress(),
                messageSystem.getAddressService().getChatClientAddress(), messageTextField.getText());
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

    public void goToSlotWindow(ActionEvent event) {
        application.gotoSlotWin();
    }

    public void goToRouletteWindow(ActionEvent event) {
        application.gotoRouletteWin();
    }

    public void startDonateWindow(ActionEvent event) throws Exception {

        DonateWindowController donate = (DonateWindowController) application.replaceSceneContent("/frontend/FXML/DonateWindow.fxml", event, "$$$");
        donate.setApp(this);

    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

}
