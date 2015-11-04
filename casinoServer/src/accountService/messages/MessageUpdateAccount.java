package accountService.messages;

import accountService.Account;
import accountService.AccountService;
import base.Address;

/**
 * Created by admin on 29.10.2015.
 */
public class MessageUpdateAccount extends MessageToAccountService {

    Account account;

    public MessageUpdateAccount(Address from, Address to, Account account) {
        super(from, to);
        this.account = account;
    }

    public void exec(AccountService accountService) {
        accountService.getAccountDataBase().updateAccount(account);
    }
}
