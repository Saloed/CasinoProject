package chatClient.messages;

import base.Abonent;
import base.Address;
import base.Message;
import chatClient.ChatClient;

public abstract class MessageToChatClient extends Message {

    MessageToChatClient(Address from, Address to) {
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
