package main;

import authorizeClient.AuthorizeClient;
import authorizeClient.messages.MessageDissconectAuthorizer;
import base.Address;
import base.Message;
import base.MessageSystem;
import chatClient.messages.MessageChatDisconnection;
import frontend.*;
import gameService.messages.MessageUserDisconect;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import messageSystem.MessageSystemImpl;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class Main extends Application {

    private final ExecutorService authorizationExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService workThreadPool = Executors.newFixedThreadPool(3);
    private MessageSystemImpl messageSystem;
    private Stage stage;
    private String userName;
    private AuthorizeController login = null;
    private RegistrationController reg = null;
    private MainWindowController home = null;
    private SlotWindowController slotMachine = null;
    private RouletteWindowController roulette = null;
    private int cash;

    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[]) null);
    }

    @Override
    public void init() throws Exception {

        messageSystem = new MessageSystemImpl();

        authorizationExecutor.execute(new AuthorizeClient(messageSystem, workThreadPool));

    }

    public void gotoLogin() {
        try {
            login = (AuthorizeController) replaceSceneContent("/frontend/FXML/AuthorizeWindow.fxml", "Authorization window");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoMainWin() {
        try {

            if (login != null) {
                login.stopController();
                login = null;
            }
            if (reg != null) {
                reg = null;
            }

            Message message = new MessageDissconectAuthorizer(new Address(),
                    messageSystem.getAddressService().getAuthorizeClientAddress());
            messageSystem.sendMessage(message);
            authorizationExecutor.shutdown();
            home = (MainWindowController) replaceSceneContent("/frontend/FXML/MainWindow.fxml", "Casino TRI TOPORA");
            home.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoSlotWin() {
        try {
            if (home != null) {
                home.stopController();
                home = null;
            }
            slotMachine = (SlotWindowController) replaceSceneContent("/frontend/FXML/SlotWindow.fxml", "YOU SHALL NOT WIN");
            slotMachine.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoRouletteWin() {
        try {
            if (home != null) {
                home.stopController();
                home = null;
            }
            roulette = (RouletteWindowController) replaceSceneContent("/frontend/FXML/RouletteWindow.fxml", "YOU SHALL NOT WIN");
            roulette.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoReg() {
        try {
            reg = (RegistrationController) replaceSceneContent("/frontend/FXML/Registration.fxml", "Registration window");
            reg.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            stage = primaryStage;
            gotoLogin();
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //U can pass any params in
    private Initializable replaceSceneContent(String fxml, String title) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        Pane page;
        try (InputStream in = Main.class.getResourceAsStream(fxml)) {
            page = loader.load(in);
        }
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    //For modal windows
    public Initializable replaceSceneContent(String fxml, ActionEvent event, String title) throws Exception {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MainWindowController.class.getResource(fxml));
        Pane page;
        try (InputStream in = MainWindowController.class.getResourceAsStream(fxml)) {
            page = loader.load(in);
        }
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        stage.show();
        return (Initializable) loader.getController();
    }

    @Override
    public void stop() throws Exception {

        System.err.println("Application stopping");
        try {
            if (login != null) {
                login.stopController();
            }
            if (reg != null) {

            }
            if (home != null) {
                home.stopController();
            }
            if (slotMachine != null) {
                slotMachine.stopController();
            }
            if (roulette != null) {
                roulette.stopController();
            }

            if (!authorizationExecutor.isShutdown()) {

                Message message = new MessageDissconectAuthorizer(new Address(),
                        messageSystem.getAddressService().getAuthorizeClientAddress());
                messageSystem.sendMessage(message);

                authorizationExecutor.shutdown();
                if (!authorizationExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                    authorizationExecutor.shutdownNow();
                    if (!authorizationExecutor.awaitTermination(1, TimeUnit.SECONDS))
                        System.err.println("Work Pool did not terminate");
                }
            }

            if (messageSystem.getAddressService().getGameClientAddress() != null) {
                Message message = new MessageChatDisconnection(new Address(),
                        messageSystem.getAddressService().getChatClientAddress());
                messageSystem.sendMessage(message);
                message = new MessageUserDisconect(new Address(),
                        messageSystem.getAddressService().getGameServiceAddress());
                messageSystem.sendMessage(message);
            }


            Thread.sleep(5000);

            workThreadPool.shutdown();


            if (!workThreadPool.awaitTermination(1, TimeUnit.SECONDS)) {
                workThreadPool.shutdownNow();
                if (!workThreadPool.awaitTermination(1, TimeUnit.SECONDS))
                    System.err.println("Work Pool did not terminate");
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println("Error when shutdown thread pool");
        }

        super.stop();
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public void takeLogin(String login) {
        this.userName = login;
    }

    public String getUserName() {
        return userName;
    }

    public void takeCash(int cash) {
        this.cash = cash;
    }

    public int getCash() {
        return cash;
    }
}
