package gameManager.messages;

import accountService.messages.MessageUpdateAccount;
import base.Address;
import base.Message;
import gameManager.GameManager;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by admin on 04.11.2015.
 */
public class MessagePlayerDisconnected extends MessageToGameManager {

    private final ChannelHandlerContext ctx;

    public MessagePlayerDisconnected(Address from, Address to, ChannelHandlerContext ctx) {
        super(from, to);
        this.ctx = ctx;
    }

    public void exec(GameManager gameManager) {
        Integer sessionId = gameManager.getPlayerSessionByContext(ctx);
        assert sessionId != null;
        gameManager.removeGame(sessionId);
        gameManager.removeUserChannel(sessionId);
        Message message = new MessageUpdateAccount(gameManager.getAddress(),
                gameManager.getMessageSystem().getAddressService().getAccountServiceAddress(),
                gameManager.getPlayer(sessionId).getAccount());
        gameManager.getMessageSystem().sendMessage(message);
        gameManager.removeUser(sessionId);
    }

}
