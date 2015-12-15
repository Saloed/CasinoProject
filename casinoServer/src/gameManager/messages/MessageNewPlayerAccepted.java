package gameManager.messages;

import base.Address;
import base.GameMessage;
import gameManager.GameManager;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;


public class MessageNewPlayerAccepted extends MessageToGameManager {
    private final ChannelHandlerContext ctx;
    private final Integer sessionId;
    private final GameMessage.ServerRequest.GameType game;
    private final List<GameMessage.ServerRequest.Bet> playerBet;
    //private Integer bet = null;
    //private Integer betCash = null;

    public MessageNewPlayerAccepted(Address from, Address to,
                                    ChannelHandlerContext ctx, GameMessage.ServerRequest msg) {
        super(from, to);
        this.ctx = ctx;
        this.sessionId = msg.getSessionId();
        this.game = msg.getGame();
        this.playerBet = msg.getBetList();
        /*if (msg.getBetCount() == 0) {
            this.bet = null;
            this.betCash = null;
        } else {

            this.betCash = msg.getBet(0).getCash();
            if (msg.getBet(0).getCoefficient() == 0)
                this.bet = null;
            else
                this.bet = msg.getBet(0).getBet(0);
        }*/
    }

    public void exec(GameManager gameManager) {
        gameManager.addUserChannel(sessionId, ctx);
        if (playerBet == null || playerBet.isEmpty()) {
            gameManager.addNewPlayer(game, sessionId, null);
        } else {
            gameManager.addNewPlayer(game, sessionId, playerBet);
        }
    }
}
