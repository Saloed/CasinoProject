package chatServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;


public final class ChatServer implements Runnable {
    private final int PORT = 1488;

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        boolean interrupted = false;
        try {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
            ServerBootstrap server = new ServerBootstrap();

            server.group(bossGroup, workerGroup);
            server.channel(NioServerSocketChannel.class);
            server.handler(new LoggingHandler(LogLevel.INFO));
            server.childHandler(new ChatServerInitializer(sslCtx));

            server.bind(PORT).sync().channel().closeFuture().sync();

        } catch (SSLException | CertificateException ce) {
            System.err.println("Error in chat security certificates");
        } catch (InterruptedException e) {
            // e.printStackTrace();
            interrupted = true;
            System.err.println("ChatServer thread was interrupted");
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
