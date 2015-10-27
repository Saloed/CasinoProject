package authorizeClient;

import base.GameMessage.UserAuthorizeMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.LinkedList;


public class AuthorizeClient implements Runnable {
    static final String HOST = "127.0.0.1";
    static final int PORT = 7776;

    private final LinkedList<Thread> threadList;

    public AuthorizeClient(LinkedList<Thread> threadList){
        this.threadList=threadList;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap();
            client.group(group);
            client.channel(NioSocketChannel.class);
            client.handler(new AuthorizeClientInitializer(threadList));

            Channel ch = client.connect(HOST, PORT).sync().channel();

            //TODO write here name and password

            UserAuthorizeMessage msg=UserAuthorizeMessage.newBuilder()
                    .setUserName("admin")
                    .setPassword("root")
                    .build();

            ChannelFuture channelFuture=ch.writeAndFlush(msg).sync();
            ch.closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
