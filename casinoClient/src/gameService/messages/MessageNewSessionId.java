package gameService.messages;

import base.Address;
import gameService.GameService;


public class MessageNewSessionId extends MessageToGameService {

    private final Integer sessionId;

    public MessageNewSessionId(Address from, Address to, Integer sessionId) {
        super(from, to);
        this.sessionId = sessionId;
    }

    public void exec(GameService service) {
        service.changeSessionId(sessionId);
    }

}
