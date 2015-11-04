package gameManager;

import base.GameMessage;
import base.Message;
import gameManager.gameMessageSystem.GameMessageSystem;
import gameManager.messages.MessageGameResult;

import java.util.LinkedList;
import java.util.Random;


public class GameRoulette extends Game {

    private LinkedList<Player> players = new LinkedList<>();

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
            if (player.getBet() == null)
                throw new IllegalArgumentException("Bet null");
            if (player.getBet().equals(result))
                resultCash = resultCash * 5;
            else
                resultCash = 0;

            resultCash = resultCash + player.getAccount().getCash();

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
        while (true) {
            messageSystem.execForAbonent(this);

            if (!players.isEmpty()) {
                play();
                players.clear();
            }
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
