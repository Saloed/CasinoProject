package gameService;

import base.Abonent;
import base.Address;
import base.GameMessage;
import io.netty.channel.Channel;
import messageSystem.MessageSystemImpl;

public class GameService implements Runnable, Abonent {
    private final Address address = new Address();
    private final MessageSystemImpl messageSystem;
    private Channel channel;
    private Integer sessionId = 0;

    public GameService(MessageSystemImpl messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    //TODO this just for test
    public void sendRequest() {
        /*//SLOT test
        GameMessage.ServerRequest msg = GameMessage.ServerRequest.newBuilder()
                .setSessionId(sessionId)
                .setGame(GameMessage.ServerRequest.GameType.SLOT)
                .addBet(30)
                .build();
                */
        //ROULETT test
        GameMessage.ServerRequest msg = GameMessage.ServerRequest.newBuilder()
                .setSessionId(sessionId)
                .setGame(GameMessage.ServerRequest.GameType.ROULETTE)
                .addBet(30)
                .addBet(17)
                .build();
        channel.writeAndFlush(msg);
    }


    public void changeSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while (true) {
            messageSystem.execForAbonent(this);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
