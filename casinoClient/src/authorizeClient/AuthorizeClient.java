package authorizeClient;

import base.Abonent;
import base.Address;
import base.GameMessage.UserAuthorizeMessage;
import base.Message;
import frontend.messages.MessageAuthorizeError;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import messageSystem.MessageSystemImpl;


public class AuthorizeClient implements Runnable, Abonent {

    //server address
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 7776;
    private final Address address = new Address();
    private final MessageSystemImpl messageSystem;
    private boolean breaker = true;
    private Bootstrap client;
    private Channel ch = null;
    private boolean needReconnect = false;

    public AuthorizeClient(MessageSystemImpl messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
    }

    public boolean readyToWork() {
        return ch != null;
    }

    public void notifyConnectionError() {
        Message message = new MessageAuthorizeError(address,
                messageSystem.getAddressService().getAuthorizeControllerAddress(),
                "Cant connect to server");
        messageSystem.sendMessage(message);
    }

    //disconnect from server
    public void channelDisconnection() {
        breaker = false;
        try {
            if (ch != null) {
                ch.close().sync();
            }
        } catch (InterruptedException e) {
            System.err.println("Error when authorize channel close");
        }
    }

    //send request to server
    public void sendRequest(UserAuthorizeMessage msg) {
        try {

            ChannelFuture channelFuture = ch.writeAndFlush(msg).sync();

        } catch (InterruptedException e) {
            System.err.println("Error when send authorization request");
        }
    }

    public Address getAddress() {
        return address;
    }

    //try connect to server
    private boolean connectionTry() {
        try {
            ch = client.connect(HOST, PORT).sync().channel();
        } catch (Exception e) {
            System.err.println("Server offline");
            ch = null;
            return true;
        }
        return false;
    }

    public void reconnect() {
        if (needReconnect) {
            needReconnect = connectionTry();
        }
    }

    public void run() {
        Thread.currentThread().setName("Authorize Client thread");
        EventLoopGroup group = new NioEventLoopGroup();
        boolean interrupted = false;

        try {
            client = new Bootstrap();
            client.group(group);
            client.channel(NioSocketChannel.class);
            client.handler(new AuthorizeClientInitializer(messageSystem));

            needReconnect = connectionTry();

            if (needReconnect) {
                Thread.sleep(20);
                notifyConnectionError();
            }
            try {
                while (breaker) {
                    messageSystem.execForAbonent(this);

                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                interrupted = true;
                System.err.println("Authorize Client throw interrupted");
            }

        } catch (InterruptedException e) {
            interrupted = true;
            System.err.println("Authorize Client throw interrupted");
        } finally {
            group.shutdownGracefully();
            if (interrupted)
                Thread.currentThread().interrupt();
            System.err.println("Authorize Client end work");
        }
    }
}
