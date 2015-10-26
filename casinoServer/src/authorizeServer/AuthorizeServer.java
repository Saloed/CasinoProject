package authorizeServer;

import base.MessageSystem;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public final class AuthorizeServer implements Runnable {

    final int PORT = 7776;

    final Thread authorizerThread;
    final MessageSystem authorizerMessageSystem;

    public AuthorizeServer(MessageSystem messageSystem){
        authorizerMessageSystem=messageSystem;
        authorizerThread=new Thread(new Authorizer(messageSystem));
        authorizerThread.setDaemon(true);
        authorizerThread.start();
    }

    public void run() {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workGroup);
            server.channel(NioServerSocketChannel.class);
            server.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
            server.handler(new LoggingHandler(LogLevel.INFO));
            server.childHandler(new AuthorizeServerInitializer(authorizerMessageSystem));

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
