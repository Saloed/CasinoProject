package gameManager;


import base.GameMessage;
import base.Message;
import gameManager.gameMessageSystem.GameMessageSystem;
import gameManager.messages.MessageGameResult;

import java.util.LinkedList;
import java.util.Random;

public final class GameSlotMachine extends Game {

    private LinkedList<Player> players = new LinkedList<>();

    public GameSlotMachine(GameMessageSystem messageSystem) {
        super(messageSystem);

    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    private void play(Player player) {
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
        int resultCash = player.getBetCash();
        if (first == secound && secound == third)
            resultCash = resultCash * 5;
        else
            resultCash = 0;
        resultCash = resultCash + player.getAccount().getCash();

        GameMessage.ServerAnswer msg = GameMessage.ServerAnswer.newBuilder()
                .setCash(resultCash)
                .addGameData(first)
                .addGameData(secound)
                .addGameData(third)
                .build();

        Message message = new MessageGameResult(messageSystem.getAddressService().getSlotMachineAddress(),
                messageSystem.getAddressService().getGameManagerAddress(),
                player.getSessioId(), msg);
        messageSystem.sendMessage(message);
    }

    @Override
    public void run() {
        while (true) {
            messageSystem.execForAbonent(this);

            if (!players.isEmpty()) {
                for (Player player : players) {
                    play(player);
                }
                players.clear();
            }
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
