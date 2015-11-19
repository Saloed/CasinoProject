package authorizeClient;

import base.GameMessage.UserAuthorizeAnswerMessage;
import base.Message;
import chatClient.messages.MessageUpdateUserName;
import frontend.messages.MessageToAuthorizeController;
import gameService.messages.MessageNewSessionId;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import messageSystem.MessageSystemImpl;

import java.util.LinkedList;


public class AuthorizeClientHandler extends SimpleChannelInboundHandler<UserAuthorizeAnswerMessage> {

    private final LinkedList<Thread> threadList;
    private final MessageSystemImpl messageSystem;

    public AuthorizeClientHandler(MessageSystemImpl messageSystem, LinkedList<Thread> threadList) {
        this.threadList = threadList;
        this.messageSystem = messageSystem;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, UserAuthorizeAnswerMessage msg) {
        System.out.println(msg.getUserName() + "    " + msg.getPassword() + "  " + msg.getAnswer());

        if (msg.getAnswer() == true) {
            for (Thread thread : threadList) {
                thread.start();
            }
            Message message = new MessageNewSessionId(messageSystem.getAddressService().getAuthorizeClientAddress(),
                    messageSystem.getAddressService().getGameServiceAddress(),
                    msg.getSessionId());
            messageSystem.sendMessage(message);

            message = new MessageUpdateUserName(messageSystem.getAddressService().getAuthorizeClientAddress(),
                    messageSystem.getAddressService().getChatClientAddress(),
                    msg.getUserName());
            messageSystem.sendMessage(message);
        }
      Message  message = new MessageToAuthorizeController(messageSystem.getAddressService().getAuthorizeClientAddress(),
                messageSystem.getAddressService().getAuthorizeControllerAddress(), msg.getUserName(), msg.getAnswer());
        messageSystem.sendMessage(message);


    }
}
