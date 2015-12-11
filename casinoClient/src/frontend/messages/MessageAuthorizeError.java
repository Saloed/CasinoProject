package frontend.messages;

import base.Abonent;
import base.Address;
import base.Message;
import frontend.AuthorizeController;

public class MessageAuthorizeError extends Message {

    private final String error;

    public MessageAuthorizeError(Address from, Address to, String error) {
        super(from, to);
        this.error = error;
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof AuthorizeController) {
            AuthorizeController authorizeController = (AuthorizeController) abonent;
            authorizeController.errorHappens(error);
        }

    }
}
