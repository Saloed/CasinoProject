package main;

import authorizeClient.AuthorizeClient;


public final class Main {
    public static void main(String[] args) {
       /* ChatClient chatClient=new ChatClient();
        Thread chatClientThread=new Thread(chatClient);

        chatClientThread.start();
    */

        Thread authorizeClientThread = new Thread(new AuthorizeClient());
        authorizeClientThread.start();

    }
}
