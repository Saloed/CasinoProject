package frontend;

import base.Abonent;
import base.Address;
import base.Message;

/**
 * Created by FedoR on 17.11.2015.
 */
public class MessagePipiska extends Message{

    private String login;
    private Boolean answer;

    public MessagePipiska(Address from, Address to,String login, Boolean answer) {
        super(from, to);
        this.login = login;
        this.answer = answer;
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof AuthorizeController)
        {
            AuthorizeController piska = (AuthorizeController) abonent;
            piska.handleAnswer(answer);
        }

    }
}
