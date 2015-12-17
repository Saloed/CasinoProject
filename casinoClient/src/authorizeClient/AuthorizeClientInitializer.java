package authorizeClient;

import base.GameMessage;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import messageSystem.MessageSystemImpl;

class AuthorizeClientInitializer extends ChannelInitializer<SocketChannel> {

    private final MessageSystemImpl messageSystem;

    public AuthorizeClientInitializer(MessageSystemImpl messageSystem) {
        this.messageSystem = messageSystem;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //Decoder
        pipeline.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
        pipeline.addLast("protobufDecoder", new ProtobufDecoder(GameMessage.UserAuthorizeAnswerMessage.getDefaultInstance()));

        //Encoder
        pipeline.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast("protubufEncoder", new ProtobufEncoder());

        pipeline.addLast("serverHandler", new AuthorizeClientHandler(messageSystem));
    }


}
