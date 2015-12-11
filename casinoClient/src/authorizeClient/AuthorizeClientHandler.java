package authorizeClient;

import base.GameMessage.UserAuthorizeAnswerMessage;
import base.Message;
import chatClient.ChatClient;
import chatClient.messages.MessageUpdateUserName;
import frontend.messages.MessageToAuthorizeController;
import gameClient.GameClient;
import gameService.GameService;
import gameService.messages.MessageNewSessionId;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import messageSystem.MessageSystemImpl;

import java.util.concurrent.ExecutorService;


class AuthorizeClientHandler extends SimpleChannelInboundHandler<UserAuthorizeAnswerMessage> {

    private final ExecutorService threadExecutor;
    private final MessageSystemImpl messageSystem;

    public AuthorizeClientHandler(MessageSystemImpl messageSystem, ExecutorService threadExecutor) {
        this.threadExecutor = threadExecutor;
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
                messageSystem.getAddressService().getAuthorizeControllerAddress(), msg.getUserName(), msg.getAnswer(), msg.getCash());
        messageSystem.sendMessage(message);
/*
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.err.println("Authorize Client Handler cant sleep ((((");
        }
*/
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


    }
}
