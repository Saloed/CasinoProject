package gameManager.messages;

import base.Address;
import base.GameMessage;
import gameManager.GameManager;
import io.netty.channel.ChannelHandlerContext;


public class MessageGameResult extends MessageToGameManager {

    private final GameMessage.Answer.ServerAnswer msg;
    private final Integer sesionId;

    public MessageGameResult(Address from, Address to, Integer sesionId, GameMessage.Answer.ServerAnswer msg) {
        super(from, to);
        this.msg = msg;
        this.sesionId = sesionId;
    }

    public void exec(GameManager gameManager) {
        ChannelHandlerContext ctx = gameManager.getUserChannel(sesionId);
        gameManager.changeAccountCash(sesionId, msg.getCash());
        ctx.writeAndFlush(GameMessage.Answer.newBuilder()
                .setSessionId(sesionId)
                .setAnswerType(GameMessage.Answer.RequestType.GAME)
                .setGameAnswer(msg)
                .build());
        // gameManager.removeGame(sesionId);
        //gameManager.removeUserChannel(sesionId);

    }

}
