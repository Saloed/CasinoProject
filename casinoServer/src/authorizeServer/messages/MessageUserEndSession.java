package authorizeServer.messages;

import authorizeServer.Authorizer;
import base.Abonent;
import base.Address;
import base.Message;

public class MessageUserEndSession extends Message {
    private final Integer sessionId;

    public MessageUserEndSession(Address from, Address to, Integer sessionId) {
        super(from, to);
        this.sessionId = sessionId;
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof Authorizer) {
            Authorizer authorizer = (Authorizer) abonent;
            authorizer.removeUserFromSession(sessionId);

        }
    }
}
