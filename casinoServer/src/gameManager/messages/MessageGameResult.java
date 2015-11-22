package gameManager.messages;

import accountService.messages.MessageUpdateAccount;
import base.Address;
import base.GameMessage;
import base.Message;
import gameManager.GameManager;
import io.netty.channel.ChannelHandlerContext;


public class MessageGameResult extends MessageToGameManager {

    private final GameMessage.ServerAnswer msg;
    private final Integer sesionId;

    public MessageGameResult(Address from, Address to, Integer sesionId, GameMessage.ServerAnswer msg) {
        super(from, to);
        this.msg = msg;
        this.sesionId = sesionId;
    }

    public void exec(GameManager gameManager) {
        ChannelHandlerContext ctx = gameManager.getUserChannel(sesionId);
        gameManager.changeAccountCash(sesionId, msg.getCash());
        ctx.writeAndFlush(msg);
       // gameManager.removeGame(sesionId);
        //gameManager.removeUserChannel(sesionId);

    }

}
