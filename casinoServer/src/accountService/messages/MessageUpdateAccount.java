package accountService.messages;

import accountService.Account;
import accountService.AccountService;
import base.Address;

public class MessageUpdateAccount extends MessageToAccountService {

    private final Account account;

    public MessageUpdateAccount(Address from, Address to, Account account) {
        super(from, to);
        this.account = account;
    }

    public void exec(AccountService accountService) {
        accountService.getAccountDataBase().updateAccount(account);
    }
}
