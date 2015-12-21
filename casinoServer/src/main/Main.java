package main;

import accountService.AccountService;
import authorizeServer.AuthorizeServer;
import gameManager.GameManager;
import gameServer.GameServer;
import messageSystem.MessageSystemImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


final class Main {

    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newFixedThreadPool(4);

        final MessageSystemImpl messageSystem = new MessageSystemImpl();

        threadPool.execute(new AccountService(messageSystem));
        threadPool.execute(new GameManager(messageSystem));
        threadPool.execute(new GameServer(messageSystem));
        threadPool.execute(new AuthorizeServer(messageSystem));

        boolean stopRequest = true;

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        while (stopRequest) {
            try {
                if ("stop server".equals(input.readLine())) {
                    stopRequest = false;
                    System.out.println("Server is stopping...");
                    threadPool.shutdown();
                    if (!threadPool.awaitTermination(1, TimeUnit.SECONDS)) {
                        threadPool.shutdownNow();
                        if (!threadPool.awaitTermination(10, TimeUnit.SECONDS))
                            System.err.println("Pool did not terminate");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error when read from terminal");
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("Error when shutdown thread pool");
            } finally {
                System.out.println("Server stopped.");
            }
        }
        System.exit(0);

    }
}
