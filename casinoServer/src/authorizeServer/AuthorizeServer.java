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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public final class AuthorizeServer implements Runnable {

    private final int PORT = 7776;

    private final ExecutorService authorizerThread = Executors.newSingleThreadExecutor();
    private final MessageSystem authorizerMessageSystem;

    public AuthorizeServer(MessageSystem messageSystem) {
        authorizerMessageSystem = messageSystem;
        authorizerThread.execute(new Authorizer(messageSystem));
    }

    public void run() {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup(2);
        boolean interrupted = false;
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workGroup);
            server.channel(NioServerSocketChannel.class);
            server.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
            server.handler(new LoggingHandler(LogLevel.INFO));
            server.childHandler(new AuthorizeServerInitializer(authorizerMessageSystem));

            ChannelFuture channel = server.bind(PORT).sync();

            channel.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            //  e.printStackTrace();
            interrupted = true;
            System.err.println("AuthorizeServer thread was interrupted");
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            authorizerThread.shutdown();
            if (interrupted)
                Thread.currentThread().interrupt();
        }

    }

}
