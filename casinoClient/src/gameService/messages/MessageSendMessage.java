package gameService.messages;

import base.Address;
import base.GameMessage;
import gameService.GameService;

public class MessageSendMessage extends MessageToGameService {
    private final String message;

    public MessageSendMessage(Address from, Address to, String message) {
        super(from, to);
        this.message = message;
    }

    @Override
    protected void exec(GameService service) {
        service.sendRequest(GameMessage.Request.newBuilder()
                .setSessionId(service.getSessionId())
                .setRequestType(GameMessage.Request.RequestType.CHAT)
                .setChatMessage(
                        GameMessage.Request.ChatMessage.newBuilder()
                                .setMessage(message)
                                .build()
                )
                .build());
    }
}
