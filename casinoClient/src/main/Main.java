package main;

import authorizeClient.AuthorizeClient;
import chatClient.ChatClient;
import frontend.FrontEnd;
import gameClient.GameClient;
import gameService.GameService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import messageSystem.MessageSystemImpl;

import java.util.LinkedList;


public final class Main extends Application {

    private MessageSystemImpl messageSystem;
    private LinkedList<Thread> threadList;

    @Override
    public void init() throws Exception {

        messageSystem = new MessageSystemImpl();

        ChatClient chatClient = new ChatClient(messageSystem);
        Thread chatClientThread = new Thread(chatClient);

        GameClient gameClient = new GameClient(messageSystem);
        Thread gameClientThread = new Thread(gameClient);

        GameService gameService = new GameService(messageSystem);
        Thread gameServiceThread = new Thread(gameService);

        //TODO create all threads and add to List

        threadList = new LinkedList<>();
        threadList.add(chatClientThread);
        threadList.add(gameClientThread);
        threadList.add(gameServiceThread);

        Thread authorizeClientThread = new Thread(new AuthorizeClient(messageSystem, threadList));
        authorizeClientThread.start();

        FrontEnd frontEnd = new FrontEnd(messageSystem);
        Thread frontEndThread = new Thread(frontEnd);
        frontEndThread.start();

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/frontend/sample.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
