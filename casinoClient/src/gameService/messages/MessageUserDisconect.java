package gameService.messages;

import base.Address;
import gameService.GameService;


public class MessageUserDisconect extends MessageToGameService {

    public MessageUserDisconect(Address from, Address to) {
        super(from, to);
    }

    public void exec(GameService gameService) {
        gameService.channelDisconnect();
    }
}
