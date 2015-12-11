package frontend.messages;

import base.Abonent;
import base.Address;
import base.Message;
import frontend.AuthorizeController;

public class MessageToAuthorizeController extends Message{

    private final String login;
    private final Boolean answer;
    private final int cash;

    public MessageToAuthorizeController(Address from, Address to, String login, Boolean answer, int cash) {
        super(from, to);
        this.login = login;
        this.answer = answer;
        this.cash = cash;
    }

    @Override
    public void exec(Abonent abonent) {

        if (abonent instanceof AuthorizeController)
        {
            AuthorizeController get = (AuthorizeController) abonent;
            get.handleAnswer(answer, login, cash);
        }

    }
}
