package accountService.messages;

import accountService.AccountService;
import base.Abonent;
import base.Address;
import base.Message;


public abstract class MessageToAccountService extends Message {

    MessageToAccountService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public final void exec(Abonent abonent) {
        if (abonent instanceof AccountService) {
            exec((AccountService) abonent);
        }
    }

    protected abstract void exec(AccountService service);
}
