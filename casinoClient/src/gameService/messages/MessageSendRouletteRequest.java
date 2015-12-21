package gameService.messages;

import base.Address;
import base.GameMessage;
import gameService.GameService;

public class MessageSendRouletteRequest extends MessageToGameService {
    private final GameMessage.Request.ServerRequest partRequest;

    public MessageSendRouletteRequest(Address from, Address to, GameMessage.Request.ServerRequest request) {
        super(from, to);
        this.partRequest = request;
    }

    @Override
    protected void exec(GameService service) {
        GameMessage.Request fullRequest = GameMessage.Request.newBuilder()
                .setSessionId(service.getSessionId())
                .setRequestType(GameMessage.Request.RequestType.GAME)
                .setGameRequest(partRequest)
                .build();
        service.sendRequest(fullRequest);
    }
}
