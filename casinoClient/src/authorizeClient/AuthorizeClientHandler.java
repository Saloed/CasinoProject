package authorizeClient;

import base.GameMessage.UserAuthorizeAnswerMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.LinkedList;


public class AuthorizeClientHandler extends SimpleChannelInboundHandler<UserAuthorizeAnswerMessage> {

    private final LinkedList<Thread> threadList;

    public AuthorizeClientHandler(LinkedList<Thread> threadList) {
        this.threadList = threadList;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, UserAuthorizeAnswerMessage msg) {
        System.out.println(msg.getUserName() + "    " + msg.getPassword() + "  " + msg.getAnswer());

        if(msg.getAnswer()==true)
            for(Thread thread:threadList){
                thread.start();
            }

    }
}
