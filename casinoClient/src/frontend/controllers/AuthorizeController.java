package frontend.controllers;


import authorizeClient.messages.MessageAuthorizeUser;
import base.Abonent;
import base.Address;
import base.Message;
import base.MessageSystem;
import frontend.WorkThread;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AuthorizeController implements Initializable, Abonent {

    private final Address address = new Address();
    private final ExecutorService worker = Executors.newSingleThreadExecutor();
    public TextField login;
    public PasswordField pass;
    public Label errorMessage;
    public Button sign;
    private Main application;
    private MessageSystem messageSystem;

    public void setApp(Main application) {
        this.application = application;
        messageSystem = application.getMessageSystem();
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);

        worker.execute(new WorkThread(messageSystem, this, "Authorize"));

    }


    public Address getAddress() {
        return address;
    }

    public void checkLogPass(ActionEvent actionEvent) throws IOException {

        if (login.getText().isEmpty() || pass.getText().isEmpty()) {
            errorMessage.setText("Username/Password is incorrect");
        } else {
            Message msg = new MessageAuthorizeUser(messageSystem.getAddressService().getAuthorizeControllerAddress(),
                    messageSystem.getAddressService().getAuthorizeClientAddress(),
                    login.getText(), pass.getText(), false);
            messageSystem.sendMessage(msg);
        }

    }

    public void errorHappens(String error) {

        Platform.runLater(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                errorMessage.setText(error);
                return null;
            }
        });
    }

    public void handleAnswer(Boolean answer, String login, int cash, int sessionId) {
        Platform.runLater(new Task<Void>() {
            @Override
            protected Void call() throws Exception {


                if (answer) {
                    application.setSessionId(sessionId);
                    application.takeCash(cash);
                    application.takeLogin(login);
                    application.gotoMainWin();
                } else {
                    errorMessage.setText("Username/Password is incorrect");
                }
                return null;
            }
        });

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        errorMessage.setText("");
        login.setPromptText("Login");
        pass.setPromptText("Password");

    }

    public void gotoReg(ActionEvent event) {
        application.gotoReg();
    }

    public void stopController() {
        worker.shutdown();
        try {
            worker.awaitTermination(100, TimeUnit.MILLISECONDS);
            worker.shutdownNow();
        } catch (InterruptedException e) {
            System.err.println("Error when shuttdown Authorize Controller");
        }
    }
}

