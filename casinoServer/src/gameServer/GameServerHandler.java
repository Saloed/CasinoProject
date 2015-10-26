package gameServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import base.GameMessage.ServerRequest;


public class GameServerHandler extends SimpleChannelInboundHandler<ServerRequest> {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        //TODO Do smth (i dont know)
        System.out.println("New active channel" + ctx.toString());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ServerRequest msg) {
        ctx.writeAndFlush(msg);
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
