package chatClient;


import base.Abonent;
import base.Address;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import messageSystem.MessageSystemImpl;

public class ChatClient implements Runnable, Abonent {
    static final String HOST = "127.0.0.1";
    static final int PORT = 1488;
    private final Address address = new Address();
    private final MessageSystemImpl messageSystem;

    private String userName;

    public ChatClient(MessageSystemImpl messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
    }

    public Address getAddress() {
        return address;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private boolean breaker = true;

    public void channelDisconnection() {
        breaker = false;
    }

    public void handleMessage(String message) {
        lastWriteFuture = ch.writeAndFlush(userName + "> " + message + "\r\n");
    }

    private ChannelFuture lastWriteFuture = null;
    Channel ch = null;

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            final SslContext sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            Bootstrap client = new Bootstrap();
            client.group(group);
            client.channel(NioSocketChannel.class);
            client.handler(new ChatClientInitializer(sslCtx));

            ch = client.connect(HOST, PORT).sync().channel();
            while (breaker) {
                messageSystem.execForAbonent(this);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            // Wait until all messages are flushed before closing the channel.
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }


    }

}
