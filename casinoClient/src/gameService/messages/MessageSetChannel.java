package gameService.messages;

import base.Address;
import gameService.GameService;
import io.netty.channel.Channel;

/**
 * Created by admin on 29.10.2015.
 */
public class MessageSetChannel extends MessageToGameService {
    private final Channel channel;

    public MessageSetChannel(Address from, Address to, Channel channel) {
        super(from, to);
        this.channel = channel;
    }

    public void exec(GameService gameService) {
        gameService.setChannel(channel);
    }
}
