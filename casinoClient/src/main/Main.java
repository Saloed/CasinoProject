package main;

import authorizeClient.AuthorizeClient;
import chatClient.ChatClient;
import gameClient.GameClient;
import gameService.GameService;
import messageSystem.MessageSystemImpl;

import java.util.LinkedList;


public final class Main {
    public static void main(String[] args) {

        MessageSystemImpl messageSystem = new MessageSystemImpl();

        ChatClient chatClient = new ChatClient();
        Thread chatClientThread = new Thread(chatClient);

        GameClient gameClient = new GameClient(messageSystem);
        Thread gameClientThread = new Thread(gameClient);

        GameService gameService = new GameService(messageSystem);
        Thread gameServiceThread = new Thread(gameService);

        //TODO create all threads and add to List

        LinkedList<Thread> threadList = new LinkedList<>();
        threadList.add(chatClientThread);
        threadList.add(gameClientThread);
        threadList.add(gameServiceThread);

        Thread authorizeClientThread = new Thread(new AuthorizeClient(messageSystem, threadList));
        authorizeClientThread.start();

    }
}
