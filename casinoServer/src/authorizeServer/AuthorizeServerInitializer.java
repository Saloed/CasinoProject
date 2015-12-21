package authorizeServer;

import base.GameMessage.UserAuthorizeMessage;
import base.MessageSystem;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;


class AuthorizeServerInitializer extends ChannelInitializer<SocketChannel> {


    private final MessageSystem authorizerMessageSystem;

    public AuthorizeServerInitializer(MessageSystem authorizerMessageSystem) {
        this.authorizerMessageSystem = authorizerMessageSystem;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //Decoder
        pipeline.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
        pipeline.addLast("protobufDecoder", new ProtobufDecoder(UserAuthorizeMessage.getDefaultInstance()));

        //Encoder
        pipeline.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast("protobufEncoder", new ProtobufEncoder());

        pipeline.addLast("serverHandler", new AuthorizeServerHandler(authorizerMessageSystem));
    }
}
