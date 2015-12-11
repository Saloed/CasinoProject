package chatClient.messages;

import base.Address;
import chatClient.ChatClient;

public class MessageSendMessage extends MessageToChatClient {
    private final String message;

    public MessageSendMessage(Address from, Address to, String message) {
        super(from, to);
        this.message = message;
    }

    public void exec(ChatClient chatClient) {
        chatClient.handleMessage(message);
    }
}
