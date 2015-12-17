package gameService;

import base.Abonent;
import base.Address;
import base.GameMessage;
import base.MessageSystem;
import io.netty.channel.Channel;
import messageSystem.MessageSystemImpl;

public class GameService implements Runnable, Abonent {
    private final Address address = new Address();
    private final MessageSystemImpl messageSystem;
    private Channel channel;
    private Integer sessionId = 0;
    private GameType currentGame = GameType.NO_GAME;

    public GameService(MessageSystemImpl messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
    }

    public void changeCurrentGame(GameType newGame) {
        currentGame = newGame;
    }

    public GameType getCurrentGame() {
        return currentGame;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
        GameMessage.ServerRequest request = GameMessage.ServerRequest.newBuilder()
                .setSessionId(sessionId)
                .setGame(GameMessage.ServerRequest.GameType.NO_GAME)
                .build();
        sendRequest(request);

    }

    public void sendRequest(GameMessage.ServerRequest msg) {

       /* //SLOT test
        GameMessage.ServerRequest msg = GameMessage.ServerRequest.newBuilder()
                .setSessionId(sessionId)
                .setGame(GameMessage.ServerRequest.GameType.SLOT)
                .addBet(30)
                .build();
        */
       /* //ROULETT test
        GameMessage.ServerRequest msg = GameMessage.ServerRequest.newBuilder()
                .setSessionId(sessionId)
                .setGame(GameMessage.ServerRequest.GameType.ROULETTE)
                .addBet(30) //set null to leave game
                .addBet(17)
                .build();
*/
        /*//leave test
        GameMessage.ServerRequest msg = GameMessage.ServerRequest.newBuilder()
                .setSessionId(sessionId)
                .setGame(GameMessage.ServerRequest.GameType.SLOT)
                .build();
         */

        channel.writeAndFlush(msg);
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void channelDisconnect() {
        try {
            channel.disconnect().sync();
        } catch (InterruptedException e) {
            // e.printStackTrace();
            System.err.println("Error when disconnect game client");
        }

    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public void changeSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        boolean interrupt = false;
        try {
            while (true) {
                messageSystem.execForAbonent(this);

                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            //  e.printStackTrace();
            System.err.println("Game Service was interrupted");
            interrupt = true;
        } finally {
            if (interrupt)
                Thread.currentThread().interrupt();

        }

    }

    public enum GameType {
        SLOT,
        ROULETTE,
        NO_GAME

    }
}
