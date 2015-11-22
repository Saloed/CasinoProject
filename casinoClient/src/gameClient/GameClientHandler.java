package gameClient;

import base.GameMessage.ServerAnswer;
import base.Message;
import gameService.messages.MessageGameResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import messageSystem.MessageSystemImpl;

class GameClientHandler extends SimpleChannelInboundHandler<ServerAnswer> {


    private final MessageSystemImpl messageSystem;

    public GameClientHandler(MessageSystemImpl messageSystem) {
        this.messageSystem = messageSystem;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ServerAnswer msg) {
        Message message = new MessageGameResult(messageSystem.getAddressService().getGameClientAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), msg);
        messageSystem.sendMessage(message);
    }
}
