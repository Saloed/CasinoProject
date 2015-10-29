package gameManager;


import accountService.Account;
import base.GameMessage;
import base.Message;
import base.MessageSystem;

import java.util.Random;

public final class GameSlotMachine extends Game {

    private Account player;

    public GameSlotMachine(MessageSystem messageSystem, Integer sessionId, Account player) {
        super(messageSystem, sessionId);
        this.player = player;

    }

    @Override
    public void run() {

        Random random = new Random();
        int first = random.nextInt() % 5 + random.nextInt() % 5;
        if (first < 0)
            first = first * (-1);
        int secound = random.nextInt() % 5 + random.nextInt() % 5;
        if (secound < 0)
            secound = secound * (-1);
        int third = random.nextInt() % 5 + random.nextInt() % 5;
        if (third < 0)
            third = third * (-1);
        int resultCash = player.getCash();
        if (first == secound && secound == third)
            resultCash = resultCash * 5;
        else
            resultCash = resultCash / 2;

        GameMessage.ServerAnswer msg = GameMessage.ServerAnswer.newBuilder()
                .setCash(resultCash)
                .addGameData(first)
                .addGameData(secound)
                .addGameData(third)
                .build();

        Message message = new MessageGameResult(messageSystem.getAddressService().getGameManagerAddress(),
                messageSystem.getAddressService().getGameManagerAddress(),
                sessionId, msg);
        messageSystem.sendMessage(message);

    }
}
