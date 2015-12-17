package authorizeClient;

import base.GameMessage.UserAuthorizeAnswerMessage;
import base.Message;
import frontend.messages.MessageToAuthorizeController;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import messageSystem.MessageSystemImpl;


class AuthorizeClientHandler extends SimpleChannelInboundHandler<UserAuthorizeAnswerMessage> {

    private final MessageSystemImpl messageSystem;

    public AuthorizeClientHandler(MessageSystemImpl messageSystem) {
        this.messageSystem = messageSystem;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, UserAuthorizeAnswerMessage msg) {
        System.out.println(msg.getUserName() + "    " + msg.getPassword() + "  " + msg.getAnswer() + " " + msg.getCash());

        Message message = new MessageToAuthorizeController(messageSystem.getAddressService().getAuthorizeClientAddress(),
                messageSystem.getAddressService().getAuthorizeControllerAddress(), msg.getUserName(), msg.getAnswer(),
                msg.getCash(), msg.getSessionId());
        messageSystem.sendMessage(message);
        //if user authorized starts all services
        /*
        if (msg.getAnswer()) {
            threadExecutor.execute(new GameClient(messageSystem));
            threadExecutor.execute(new ChatClient(messageSystem));
            threadExecutor.execute(new GameService(messageSystem));
            message = new MessageNewSessionId(messageSystem.getAddressService().getAuthorizeClientAddress(),
                    messageSystem.getAddressService().getGameServiceAddress(),
                    msg.getSessionId());
            messageSystem.sendMessage(message);

            message = new MessageUpdateUserName(messageSystem.getAddressService().getAuthorizeClientAddress(),
                    messageSystem.getAddressService().getChatClientAddress(),
                    msg.getUserName());
            messageSystem.sendMessage(message);
        }

*/
    }
}
