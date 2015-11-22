package gameClient;

import base.Abonent;
import base.Address;
import base.Message;
import gameService.messages.MessageSetChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import messageSystem.MessageSystemImpl;

/**
 * Created by admin on 28.10.2015.
 */
public class GameClient implements Runnable, Abonent {
    private final Address address = new Address();
    private final MessageSystemImpl messageSystem;

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 7777;

    public GameClient(MessageSystemImpl messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
    }

    public Address getAddress() {
        return address;
    }


    public void run() {

        EventLoopGroup group = new NioEventLoopGroup();
        boolean interrupted = false;

        try {
            Bootstrap client = new Bootstrap();
            client.group(group);
            client.channel(NioSocketChannel.class);
            client.handler(new GameClientInitializer(messageSystem));

            Channel ch = client.connect(HOST, PORT).sync().channel();

            Message message = new MessageSetChannel(address,
                    messageSystem.getAddressService().getGameServiceAddress(), ch);
            messageSystem.sendMessage(message);

            ch.closeFuture().sync();

        } catch (InterruptedException e) {
            //e.printStackTrace();
            interrupted = true;
            System.err.println("Game Client throw interrupted");
        } finally {
            group.shutdownGracefully();
            if (interrupted)
                Thread.currentThread().interrupt();
            System.err.println("Game Client end work");
        }

    }
}
