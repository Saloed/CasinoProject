package accountService.messages;

import accountService.Account;
import accountService.AccountService;
import authorizeServer.messages.MessageIsAuthenticated;
import base.Address;
import base.Message;



public final class MessageAuthenticate extends MessageToAccountService {


    private String name;
    private String password;


    public MessageAuthenticate(Address from, Address to, String name, String password) {
        super(from, to);
        this.name = name;
        this.password = password;

    }

    @Override
    protected void exec(AccountService service) {
        final Account account = service.getAccountDataBase().authenticate(name, password);

        final Message back = new MessageIsAuthenticated(getTo(), getFrom(), account);
        service.getMessageSystem().sendMessage(back);
    }
}
