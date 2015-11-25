package main;

import authorizeClient.AuthorizeClient;
import authorizeClient.messages.MessageDissconectAuthorizer;
import base.Address;
import base.Message;
import base.MessageSystem;
import chatClient.messages.MessageChatDisconnection;
import frontend.AuthorizeController;
import frontend.MainWindowController;
import frontend.RegistrationController;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class Main extends Application {

    private MessageSystemImpl messageSystem;
    private final ExecutorService authorizationExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService workThreadPool = Executors.newFixedThreadPool(3);
    private Stage stage;
    private String userName;
    private AuthorizeController login = null;
    private RegistrationController reg = null;
    private MainWindowController home = null;

    @Override
    public void init() throws Exception {

        messageSystem = new MessageSystemImpl();

        authorizationExecutor.execute(new AuthorizeClient(messageSystem, workThreadPool));

    }


    private void gotoLogin() {
        try {
            login = (AuthorizeController) replaceSceneContent("/frontend/FXML/AuthorizeWindow.fxml", "Authorization window", false);
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoMainWin() {
        try {
            home = (MainWindowController) replaceSceneContent("/frontend/FXML/MainWindow.fxml",
                    "Casino TRI TOPORA", true);
            home.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoReg() {
        try {
            reg = (RegistrationController) replaceSceneContent("/frontend/FXML/Registration.fxml",
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
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        Pane page;
        try (InputStream in = Main.class.getResourceAsStream(fxml)) {
            page = loader.load(in);
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

        System.err.println("Application stopping");

        if (login != null) {
            login.stopController();
        }
        if (reg != null) {

        }
        if (home != null) {
            home.stopController();
        }


        Message message = new MessageDissconectAuthorizer(new Address(),
                messageSystem.getAddressService().getAuthorizeClientAddress());
        messageSystem.sendMessage(message);

        if (messageSystem.getAddressService().getGameClientAddress() != null) {
            message = new MessageChatDisconnection(new Address(),
                    messageSystem.getAddressService().getChatClientAddress());
            messageSystem.sendMessage(message);
            message = new MessageUserDisconect(new Address(),
                    messageSystem.getAddressService().getGameServiceAddress());
            messageSystem.sendMessage(message);
        }

        try {
            Thread.sleep(5000);

            authorizationExecutor.shutdown();
            workThreadPool.shutdown();


            if (!workThreadPool.awaitTermination(5, TimeUnit.SECONDS)) {
                workThreadPool.shutdownNow();
                if (!workThreadPool.awaitTermination(5, TimeUnit.SECONDS))
                    System.err.println("Work Pool did not terminate");
            }
            if (!authorizationExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                authorizationExecutor.shutdownNow();
                if (!authorizationExecutor.awaitTermination(5, TimeUnit.SECONDS))
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
}
