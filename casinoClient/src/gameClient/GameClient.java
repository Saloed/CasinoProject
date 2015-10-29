package gameClient;

import base.Abonent;
import base.Address;
import base.Message;
import gameService.MessageSetChannel;
import gameService.MessageTestSendRequest;
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

    static final String HOST = "127.0.0.1";
    static final int PORT = 7777;

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
        try {
            Bootstrap client = new Bootstrap();
            client.group(group);
            client.channel(NioSocketChannel.class);
            client.handler(new GameClientInitializer(messageSystem));

            Channel ch = client.connect(HOST, PORT).sync().channel();

            Message message = new MessageSetChannel(address,
                    messageSystem.getAddressService().getGameServiceAddress(), ch);
            messageSystem.sendMessage(message);
            //TODO this for test
            //TODO next time its will be message from FrontEnd to GameService

            Message msg = new MessageTestSendRequest(address, messageSystem.getAddressService().getGameServiceAddress());
            messageSystem.sendMessage(msg);

            ch.closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}
