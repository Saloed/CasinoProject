package authorizeClient;

import base.Abonent;
import base.Address;
import base.GameMessage.UserAuthorizeMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import messageSystem.MessageSystemImpl;

import java.util.LinkedList;


public class AuthorizeClient implements Runnable, Abonent {

    private final Address address = new Address();
    private final MessageSystemImpl messageSystem;


    static final String HOST = "127.0.0.1";
    static final int PORT = 7776;

    private final LinkedList<Thread> threadList;

    public AuthorizeClient(MessageSystemImpl messageSystem, LinkedList<Thread> threadList) {
        this.threadList = threadList;
        this.messageSystem = messageSystem;
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
    }

    public Address getAddress() {
        return address;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap();
            client.group(group);
            client.channel(NioSocketChannel.class);
            client.handler(new AuthorizeClientInitializer(messageSystem, threadList));

            Channel ch = client.connect(HOST, PORT).sync().channel();

            //TODO write here name and password

            UserAuthorizeMessage msg = UserAuthorizeMessage.newBuilder()
                    .setUserName("admin")
                    .setPassword("root")
                    .setRegister(false)
                    .build();

            ChannelFuture channelFuture = ch.writeAndFlush(msg).sync();
            ch.closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
