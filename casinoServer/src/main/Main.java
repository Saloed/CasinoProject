package main;

import accountService.AccountService;
import authorizeServer.AuthorizeServer;
import chatServer.ChatServer;
import gameManager.GameManager;
import gameServer.GameServer;
import messageSystem.MessageSystemImpl;


public final class Main {

    public static void main(String[] args) {
        final MessageSystemImpl messageSystem = new MessageSystemImpl();

        final Thread accountServiceThread = new Thread(new AccountService(messageSystem));
        accountServiceThread.setDaemon(true);

        final Thread gameManagerThread = new Thread(new GameManager(messageSystem));
        gameManagerThread.setDaemon(true);


        final ChatServer chatServer = new ChatServer();
        final Thread chatServerThread = new Thread(chatServer);

        final GameServer gameServer = new GameServer();
        final Thread gameServerThread = new Thread(gameServer);

        final Thread authorizeServerThread = new Thread(new AuthorizeServer(messageSystem));

        try {
            authorizeServerThread.start();
            gameServerThread.start();
            chatServerThread.start();


            accountServiceThread.start();
            gameManagerThread.start();

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }


    }
}
