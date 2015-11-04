package gameManager;

import base.GameMessage;
import base.Message;
import gameManager.gameMessageSystem.GameMessageSystem;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by user on 04.11.15.
 */
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
            int resultCash = player.getAccount().getCash();
            if (player.getBet() == null)
                throw new IllegalArgumentException("Bet null");
            if (player.getBet().equals(result))
                resultCash = resultCash * 5;
            else
                resultCash = resultCash / 2;

            GameMessage.ServerAnswer msg = GameMessage.ServerAnswer.newBuilder()
                    .setCash(resultCash)
                    .addGameData(result)
                    .build();

            Message message = new MessageGameResult(messageSystem.getAddressService().getSlotMachineAddress(),
                    messageSystem.getAddressService().getGameManagerAddress(),
                    player.getSessioId(), msg);
            messageSystem.sendMessage(message);
        }
    }

    @Override
    public void run() {

        messageSystem.execForAbonent(this);

        if (!players.isEmpty())
            play();


    }

}
