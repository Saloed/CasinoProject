package chatClient.messages;


import base.Address;
import chatClient.ChatClient;

public class MessageChatDisconnection extends MessageToChatClient {

    public MessageChatDisconnection(Address from, Address to) {
        super(from, to);
    }

    public void exec(ChatClient chatClient) {
        chatClient.channelDisconnection();
    }
}
