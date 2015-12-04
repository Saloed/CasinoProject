package gameManager;

import base.GameMessage;
import base.Message;
import gameManager.gameMessageSystem.GameMessageSystem;
import gameManager.messages.MessageGameResult;

import java.util.LinkedList;
import java.util.Random;


public class GameRoulette extends Game {

    private final LinkedList<Player> players = new LinkedList<>();

    public GameRoulette(GameMessageSystem messageSystem) {
        super(messageSystem);

    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    private void play() {
        Random random = new Random();

        int result = random.nextInt() % 9 + random.nextInt() % 9 + random.nextInt() % 9 + random.nextInt() % 9;
        if (result < 0)
            result = result * (-1);

        for (Player player : players) {
            int resultCash = player.getBetCash();
            player.getAccount().setCash(player.getAccount().getCash() - resultCash);
            if (player.getBet() == null)
                throw new IllegalArgumentException("Bet null");
            if (player.getBet().equals(result))
                resultCash = resultCash * 5;
            else
                resultCash = 0;

            resultCash = resultCash + player.getAccount().getCash();
            if (resultCash < 0)
                resultCash = 0;
            player.getAccount().setCash(resultCash);
            GameMessage.ServerAnswer msg = GameMessage.ServerAnswer.newBuilder()
                    .setCash(resultCash)
                    .addGameData(result)
                    .build();

            Message message = new MessageGameResult(messageSystem.getAddressService().getRouletteAddress(),
                    messageSystem.getAddressService().getGameManagerAddress(),
                    player.getSessioId(), msg);
            messageSystem.sendMessage(message);
        }
    }

    @Override
    public void run() {
        boolean interrupted = false;
        try {
            while (true) {
                messageSystem.execForAbonent(this);

                if (!players.isEmpty()) {
                    play();
                    players.clear();
                }

                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            //  e.printStackTrace();
            //Thread.currentThread().interrupt();
            interrupted = true;
            System.err.println("Roulette game thread was interrupted");
        } finally {
            if (interrupted)
                Thread.currentThread().interrupt();
        }

    }

}
