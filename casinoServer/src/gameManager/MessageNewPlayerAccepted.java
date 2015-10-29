package gameManager;

import base.Address;
import base.GameMessage;
import io.netty.channel.ChannelHandlerContext;


public class MessageNewPlayerAccepted extends MessageToGameManager {
    final ChannelHandlerContext ctx;
    final Integer sessionId;
    final GameMessage.ServerRequest.GameType game;

    public MessageNewPlayerAccepted(Address from, Address to,
                                    ChannelHandlerContext ctx, GameMessage.ServerRequest msg) {
        super(from, to);
        this.ctx = ctx;
        this.sessionId = msg.getSessionId();
        this.game = msg.getGame();
    }

    public void exec(GameManager gameManager) {
        gameManager.addUserChannel(sessionId, ctx);

        Game newGame = null;
        gameManager.addNewGame(game,sessionId);

    }
}
