package accountService;

import authorizeServer.MessageIsAuthenticated;
import base.Address;
import base.Message;

/**
 * Created by admin on 28.10.2015.
 */
public class MessageRegister extends MessageToAccountService {

    private String name;
    private String password;


    public MessageRegister(Address from, Address to, String name, String password) {
        super(from, to);
        this.name = name;
        this.password = password;

    }

    @Override
    protected void exec(AccountService service) {
        final Account account = service.getAccountDataBase().register(name, password);
        final Message back = new MessageIsAuthenticated(getTo(), getFrom(), account);
        service.getMessageSystem().sendMessage(back);
    }
}
