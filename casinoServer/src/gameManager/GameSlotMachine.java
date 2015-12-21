package gameManager;


import base.GameMessage;
import base.Message;
import gameManager.gameMessageSystem.GameMessageSystem;
import gameManager.messages.MessageGameResult;

import java.util.LinkedList;
import java.util.Random;

public final class GameSlotMachine extends Game {

    private final LinkedList<Player> players = new LinkedList<>();

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
        int second = random.nextInt() % 5 + random.nextInt() % 5;
        if (second < 0)
            second = second * (-1);
        int third = random.nextInt() % 5 + random.nextInt() % 5;
        if (third < 0)
            third = third * (-1);
        int resultCash = player.getBet().get(0).getCash();
        player.getAccount().setCash(player.getAccount().getCash() - resultCash);
        if (first == second && second == third)
            resultCash = resultCash * 5;
        else
            resultCash = 0;
        resultCash = resultCash + player.getAccount().getCash();
        if (resultCash < 0)
            resultCash = 0;
        player.getAccount().setCash(resultCash);

        GameMessage.Answer.ServerAnswer msg = GameMessage.Answer.ServerAnswer.newBuilder()
                .setCash(resultCash)
                .addGameData(first)
                .addGameData(second)
                .addGameData(third)
                .build();

        Message message = new MessageGameResult(messageSystem.getAddressService().getSlotMachineAddress(),
                messageSystem.getAddressService().getGameManagerAddress(),
                player.getSessionId(), msg);
        messageSystem.sendMessage(message);
    }

    @Override
    public void run() {
        boolean interrupted = false;

        try {
            while (true) {
                messageSystem.execForAbonent(this);

                if (!players.isEmpty()) {
                    players.forEach(this::play);
                    players.clear();
                }

                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            // e.printStackTrace();
            interrupted = true;
            System.err.println("Slot machine game thread was interrupted");
        } finally {
            if (interrupted)
                Thread.currentThread().interrupt();
        }

    }
}
