package chatClient.messages;

import base.Address;
import chatClient.ChatClient;

/**
 * Created by admin on 04.11.2015.
 */
public class MessageSendMessage extends MessageToChatClient {
    private String message;

    public MessageSendMessage(Address from, Address to, String message) {
        super(from, to);
        this.message = message;
    }

    public void exec(ChatClient chatClient) {
        chatClient.handleMessage(message);
    }
}
