package accountService.messages;

import accountService.Account;
import accountService.AccountService;
import authorizeServer.messages.MessageIsAuthenticated;
import base.Address;
import base.Message;

public class MessageRegister extends MessageToAccountService {

    private final String name;
    private final String password;


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
