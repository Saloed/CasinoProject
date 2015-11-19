package frontend.messages;

import base.Abonent;
import base.Address;
import base.Message;
import frontend.AuthorizeController;

/**
 * Created by FedoR on 17.11.2015.
 */
public class MessageToAuthorizeController extends Message{

    private String login;
    private Boolean answer;

    public MessageToAuthorizeController(Address from, Address to, String login, Boolean answer) {
        super(from, to);
        this.login = login;
        this.answer = answer;
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof AuthorizeController)
        {
            AuthorizeController get = (AuthorizeController) abonent;
            get.handleAnswer(answer,login);
        }

    }
}
