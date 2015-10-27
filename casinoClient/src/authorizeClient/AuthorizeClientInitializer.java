package authorizeClient;

import base.GameMessage.UserAuthorizeAnswerMessage;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.util.LinkedList;

public class AuthorizeClientInitializer extends ChannelInitializer<SocketChannel> {

    private final LinkedList<Thread> threadList;

    public AuthorizeClientInitializer(LinkedList<Thread> threadList){
        this.threadList=threadList;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //Decoder
        pipeline.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
        pipeline.addLast("protobufDecoder", new ProtobufDecoder(UserAuthorizeAnswerMessage.getDefaultInstance()));

        //Encoder
        pipeline.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast("protubufEncoder", new ProtobufEncoder());

        pipeline.addLast("serverHandler", new AuthorizeClientHandler(threadList));//authorizerMesasgeSystem));
    }


}
