package authorizeClient.messages;

import authorizeClient.AuthorizeClient;
import base.Abonent;
import base.Address;
import base.Message;


public abstract class MessageToAuthorizeClient extends Message {
    public MessageToAuthorizeClient(Address from, Address to) {
        super(from, to);
    }

    @Override
    public final void exec(Abonent abonent) {
        if (abonent instanceof AuthorizeClient) {
            exec((AuthorizeClient) abonent);
        }
    }

    protected abstract void exec(AuthorizeClient chatClient);
}
