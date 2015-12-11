package authorizeClient.messages;

import authorizeClient.AuthorizeClient;
import base.Address;

public class MessageDissconectAuthorizer extends MessageToAuthorizeClient {

    public MessageDissconectAuthorizer(Address from, Address to) {
        super(from, to);
    }

    public void exec(AuthorizeClient authorizeClient) {
        authorizeClient.channelDisconnection();
    }

}
