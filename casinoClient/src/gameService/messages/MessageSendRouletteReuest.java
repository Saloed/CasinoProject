package gameService.messages;

import base.Address;
import base.GameMessage;
import gameService.GameService;

public class MessageSendRouletteReuest extends MessageToGameService {
    private final GameMessage.ServerRequest partRequest;

    public MessageSendRouletteReuest(Address from, Address to, GameMessage.ServerRequest request) {
        super(from, to);
        this.partRequest = request;
    }

    @Override
    protected void exec(GameService service) {
        GameMessage.ServerRequest fullRequest = GameMessage.ServerRequest.newBuilder()
                .setSessionId(service.getSessionId())
                .setGame(partRequest.getGame())
                .addAllBet(partRequest.getBetList())
                .build();
        service.sendRequest(fullRequest);
    }
}
