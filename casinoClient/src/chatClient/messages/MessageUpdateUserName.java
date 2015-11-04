package chatClient.messages;

import base.Address;
import chatClient.ChatClient;

/**
 * Created by user on 04.11.15.
 */
public class MessageUpdateUserName extends MessageToChatClient {

    private final String userName;

    public MessageUpdateUserName(Address from, Address to, String userName) {
        super(from, to);
        this.userName = userName;
    }
    public void exec(ChatClient chatClient){
        chatClient.setUserName(userName);
    }

}
