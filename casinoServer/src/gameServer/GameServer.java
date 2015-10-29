package gameServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import messageSystem.MessageSystemImpl;


public final class GameServer implements Runnable {

    final int PORT = 7777;

final MessageSystemImpl messageSystem;
    public GameServer(MessageSystemImpl messageSystem){
        this.messageSystem=messageSystem;
    }
    public void run() {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup(4);


        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workGroup);
            server.channel(NioServerSocketChannel.class);
            server.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
            //TODO More option mb required
            server.handler(new LoggingHandler(LogLevel.INFO));
            server.childHandler(new GameServerInitializer(messageSystem));

            ChannelFuture channel = server.bind(PORT).sync();

            channel.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

}
