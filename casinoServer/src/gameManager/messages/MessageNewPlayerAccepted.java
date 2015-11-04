package gameManager.messages;

import base.Address;
import base.GameMessage;
import gameManager.GameManager;
import io.netty.channel.ChannelHandlerContext;


public class MessageNewPlayerAccepted extends MessageToGameManager {
    final ChannelHandlerContext ctx;
    final Integer sessionId;
    final GameMessage.ServerRequest.GameType game;
    private Integer bet = null;
    private Integer betCash = null;

    public MessageNewPlayerAccepted(Address from, Address to,
                                    ChannelHandlerContext ctx, GameMessage.ServerRequest msg) {
        super(from, to);
        this.ctx = ctx;
        this.sessionId = msg.getSessionId();
        this.game = msg.getGame();
        if (msg.getBetCount() == 0) {
            this.bet = null;
            this.betCash = null;
        } else {

            this.betCash = msg.getBet(0);
            if (msg.getBetCount() >= 2)
                this.bet = msg.getBet(1);
            else
                this.bet = null;
        }
    }

    public void exec(GameManager gameManager) {
        gameManager.addUserChannel(sessionId, ctx);
        gameManager.addNewPlayer(game, sessionId, betCash, bet);

    }
}
