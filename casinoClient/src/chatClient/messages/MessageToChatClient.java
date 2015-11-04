package chatClient.messages;

import base.Abonent;
import base.Address;
import base.Message;
import chatClient.ChatClient;
import gameService.GameService;

/**
 * Created by user on 04.11.15.
 */
public abstract class MessageToChatClient extends Message {

    public MessageToChatClient(Address from, Address to) {
        super(from, to);
    }

    @Override
    public final void exec(Abonent abonent) {
        if (abonent instanceof ChatClient) {
            exec((ChatClient) abonent);
        }
    }

    protected abstract void exec(ChatClient chatClient);
}
