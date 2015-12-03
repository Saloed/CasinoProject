package chatClient;

import base.Message;
import base.MessageSystem;
import frontend.messages.MessageToMainWindowController;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    final private MessageSystem messageSystem;

    ChatClientHandler(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.err.println(msg);
        Message message=new MessageToMainWindowController(messageSystem.getAddressService().getChatClientAddress(),
                messageSystem.getAddressService().getMainWindowControllerAddress(),
                msg);
        messageSystem.sendMessage(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
