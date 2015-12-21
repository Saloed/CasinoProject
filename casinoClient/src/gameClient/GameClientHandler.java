package gameClient;

import base.GameMessage;
import base.Message;
import frontend.messages.MessageToMainWindowController;
import gameService.messages.MessageGameResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import messageSystem.MessageSystemImpl;

class GameClientHandler extends SimpleChannelInboundHandler<GameMessage.Answer> {


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
    public void channelRead0(ChannelHandlerContext ctx, GameMessage.Answer msg) {
        if (GameMessage.Answer.RequestType.GAME.equals(msg.getAnswerType())) {
            Message message = new MessageGameResult(messageSystem.getAddressService().getGameClientAddress(),
                    messageSystem.getAddressService().getGameServiceAddress(), msg.getGameAnswer());
            messageSystem.sendMessage(message);
        } else {
            Message message = new MessageToMainWindowController(messageSystem.getAddressService().getGameClientAddress(),
                    messageSystem.getAddressService().getMainWindowControllerAddress(),
                    msg.getChatMessage().getMessage());
            messageSystem.sendMessage(message);
        }

    }
}
