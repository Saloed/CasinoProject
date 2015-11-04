package gameManager;

import base.Address;
import base.GameMessage;
import base.Message;
import io.netty.channel.ChannelHandlerContext;


public class MessageNewPlayerAccepted extends MessageToGameManager {
    final ChannelHandlerContext ctx;
    final Integer sessionId;
    final GameMessage.ServerRequest.GameType game;
    private  Integer bet=null;

    public MessageNewPlayerAccepted(Address from, Address to,
                                    ChannelHandlerContext ctx, GameMessage.ServerRequest msg) {
        super(from, to);
        this.ctx = ctx;
        this.sessionId = msg.getSessionId();
        this.game = msg.getGame();
        if(game!= GameMessage.ServerRequest.GameType.SLOT)
            bet=msg.getBet();

    }

    public void exec(GameManager gameManager) {
        gameManager.addUserChannel(sessionId, ctx);
        gameManager.addNewPlayer(game, sessionId,bet);

    }
}
