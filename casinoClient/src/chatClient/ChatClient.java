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

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatClient implements Runnable ,Abonent{
    static final String HOST = "127.0.0.1";
    static final int PORT = 1488;
    private final Address address = new Address();
    private final MessageSystemImpl messageSystem;

    private String userName;

public ChatClient(MessageSystemImpl messageSystem){
    this.messageSystem = messageSystem;
    messageSystem.getAddressService().register(this);
    messageSystem.addService(this);
}
    public Address getAddress() {
        return address;
    }

    public void setUserName(String userName){
        this.userName=userName;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            final SslContext sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            Bootstrap client = new Bootstrap();
            client.group(group);
            client.channel(NioSocketChannel.class);
            client.handler(new ChatClientInitializer(sslCtx));

            Channel ch = client.connect(HOST, PORT).sync().channel();

            messageSystem.execForAbonent(this);

            // Read commands from the stdin.
            ChannelFuture lastWriteFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }

                // Sends the received line to the server.
                lastWriteFuture = ch.writeAndFlush(userName+"> "+line + "\r\n");
                /*
                // If user typed the '/exit' command, wait until the server closes
                // the connection.
                if ("/exit".equals(line.toLowerCase())) {
                    ch.closeFuture().sync();
                    break;
                }*/
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
