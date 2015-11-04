package authorizeClient.messages;

import authorizeClient.AuthorizeClient;
import base.Address;

/**
 * Created by admin on 04.11.2015.
 */
public class MessageDissconectAuthorizer extends MessageToAuthorizeClient {

    public MessageDissconectAuthorizer(Address from, Address to) {
        super(from, to);
    }

    public void exec(AuthorizeClient authorizeClient) {
        authorizeClient.channelDisconnection();
    }

}
