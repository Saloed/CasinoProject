package authorizeClient;

import base.GameMessage.UserAuthorizeAnswerMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class AuthorizeClientHandler extends SimpleChannelInboundHandler<UserAuthorizeAnswerMessage> {


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, UserAuthorizeAnswerMessage msg) {
        System.out.println(msg.getUserName()+"    "+msg.getPassword()+"  "+msg.getAnswer());
    }
}
