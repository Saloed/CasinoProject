package gameService;

import base.Address;

/**
 * Created by admin on 29.10.2015.
 */
public class MessageTestSendRequest extends MessageToGameService {
    public MessageTestSendRequest(Address from, Address to) {
        super(from, to);
    }

    public void exec(GameService gameService) {
        gameService.sendRequest();
    }
}
