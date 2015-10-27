package main;

import authorizeClient.AuthorizeClient;
import chatClient.ChatClient;

import java.util.LinkedList;


public final class Main {
    public static void main(String[] args) {
        ChatClient chatClient=new ChatClient();
        Thread chatClientThread=new Thread(chatClient);

        //TODO create all threads and add to List

        LinkedList<Thread> threadList=new LinkedList<>();
        threadList.add(chatClientThread);

        Thread authorizeClientThread = new Thread(new AuthorizeClient(threadList));
        authorizeClientThread.start();

    }
}
