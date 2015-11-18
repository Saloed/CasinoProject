package main;

import authorizeClient.AuthorizeClient;
import authorizeClient.messages.MessageDissconectAuthorizer;
import base.Address;
import base.Message;
import base.MessageSystem;
import chatClient.ChatClient;
import chatClient.messages.MessageChatDisconnection;
import frontend.AuthorizeController;
import frontend.FrontEnd;
import frontend.MainWindowController;
import frontend.RegistrationController;
import gameClient.GameClient;
import gameService.GameService;
import gameService.messages.MessageUserDisconect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import messageSystem.MessageSystemImpl;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class Main extends Application {

    private MessageSystemImpl messageSystem;
    private LinkedList<Thread> threadList;
    private Stage stage;

    public String l = "admin";
    public String p = "admin";

    @Override
    public void init() throws Exception {

        messageSystem = new MessageSystemImpl();

        ChatClient chatClient = new ChatClient(messageSystem);
        Thread chatClientThread = new Thread(chatClient);
        chatClientThread.setDaemon(true);

        GameClient gameClient = new GameClient(messageSystem);
        Thread gameClientThread = new Thread(gameClient);
        gameClientThread.setDaemon(true);

        GameService gameService = new GameService(messageSystem);
        Thread gameServiceThread = new Thread(gameService);
        gameServiceThread.setDaemon(true);

        //TODO create all threads and add to List

        threadList = new LinkedList<>();
        threadList.add(chatClientThread);
        threadList.add(gameClientThread);
        threadList.add(gameServiceThread);

        Thread authorizeClientThread = new Thread(new AuthorizeClient(messageSystem, threadList));
        authorizeClientThread.setDaemon(true);
        authorizeClientThread.start();

        FrontEnd frontEnd = new FrontEnd(messageSystem);
        Thread frontEndThread = new Thread(frontEnd);
        frontEndThread.setDaemon(true);

        frontEndThread.start();

    }


    public void gotoLogin() {
        try {
            AuthorizeController login = (AuthorizeController) replaceSceneContent("/frontend/FXML/AuthorizeWindow.fxml", "Authorization window", false);
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoMainWin() {
        try {
            MainWindowController home = (MainWindowController) replaceSceneContent("/frontend/FXML/MainWindow.fxml",
                    "Casino TRI TOPORA", true);
            home.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoReg() {
        try {
            RegistrationController reg = (RegistrationController) replaceSceneContent("/frontend/FXML/Registration.fxml",
                    "Registration window", false);
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
    private Initializable replaceSceneContent(String fxml, String title, Boolean resizable) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        Pane page;
        try {
            page = (Pane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(resizable);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[]) null);
    }

    @Override
    public void stop() throws Exception {
        Message message = new MessageChatDisconnection(new Address(),
                messageSystem.getAddressService().getChatClientAddress());
        messageSystem.sendMessage(message);
        message = new MessageUserDisconect(new Address(),
                messageSystem.getAddressService().getGameServiceAddress());
        messageSystem.sendMessage(message);
        message = new MessageDissconectAuthorizer(new Address(),
                messageSystem.getAddressService().getAuthorizeClientAddress());
        messageSystem.sendMessage(message);
/*
        for (Thread thread : threadList) {
            thread.interrupt();
        }
  */

        super.stop();
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }
}
