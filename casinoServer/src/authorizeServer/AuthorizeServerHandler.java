package authorizeServer;

import base.GameMessage.UserAuthorizeMessage;
import base.Message;
import base.MessageSystem;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class AuthorizeServerHandler extends SimpleChannelInboundHandler<UserAuthorizeMessage> {

    final MessageSystem authorizerMessageSystem;

    public AuthorizeServerHandler(MessageSystem authorizerMessageSystem) {
        this.authorizerMessageSystem = authorizerMessageSystem;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        System.out.println("New active channel" + ctx.toString());
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

    @Override
    public void channelRead0(ChannelHandlerContext ctx, UserAuthorizeMessage msg) {
        Message mes = new MessageToAuthorizer(authorizerMessageSystem.getAddressService().getAuthorizerAddress(),
                authorizerMessageSystem.getAddressService().getAuthorizerAddress(),
                ctx, msg);
        authorizerMessageSystem.sendMessage(mes);
    }

}
