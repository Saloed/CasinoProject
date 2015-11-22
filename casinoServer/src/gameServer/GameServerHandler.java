package gameServer;

import base.GameMessage.ServerRequest;
import base.Message;
import gameManager.messages.MessageNewPlayerAccepted;
import gameManager.messages.MessagePlayerDisconnected;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import messageSystem.MessageSystemImpl;


class GameServerHandler extends SimpleChannelInboundHandler<ServerRequest> {
    private final MessageSystemImpl messageSystem;

    public GameServerHandler(MessageSystemImpl messageSystem) {
        this.messageSystem = messageSystem;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        System.out.println("New active channel" + ctx.toString());
    }

    @Override
    public void channelInactive(final ChannelHandlerContext ctx) {
        System.out.println("Disconnected " + ctx.toString());

        Message message = new MessagePlayerDisconnected(messageSystem.getAddressService().getGameManagerAddress(),
                messageSystem.getAddressService().getGameManagerAddress(),
                ctx);
       // System.out.println(ctx.channel().remoteAddress());
        messageSystem.sendMessage(message);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ServerRequest msg) {

        Message message = new MessageNewPlayerAccepted(messageSystem.getAddressService().getGameManagerAddress(),
                messageSystem.getAddressService().getGameManagerAddress(),
                ctx, msg);
        messageSystem.sendMessage(message);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
