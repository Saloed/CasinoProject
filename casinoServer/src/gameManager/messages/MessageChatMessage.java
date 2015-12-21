package gameManager.messages;

import base.Address;
import base.GameMessage;
import gameManager.GameManager;
import gameManager.Player;
import io.netty.channel.ChannelHandlerContext;

public class MessageChatMessage extends MessageToGameManager {
    private final GameMessage.Request request;

    public MessageChatMessage(Address from, Address to, GameMessage.Request msg) {
        super(from, to);
        this.request = msg;
    }

    @Override
    protected void exec(GameManager gameMechanics) {
        Player player = gameMechanics.getPlayer(request.getSessionId());
        if (player != null) {
            String userName = player.getAccount().getName();
            GameMessage.Answer answer = GameMessage.Answer.newBuilder()
                    .setSessionId(request.getSessionId())
                    .setAnswerType(GameMessage.Answer.RequestType.CHAT)
                    .setChatMessage(GameMessage.Answer.ChatAnswer.newBuilder()
                            .setMessage(userName + ">" + request.getChatMessage().getMessage())
                            .build())
                    .build();
            for (ChannelHandlerContext ctx : gameMechanics.getUsersChannels().values()) {
                ctx.writeAndFlush(answer);
            }

        }

    }
}
