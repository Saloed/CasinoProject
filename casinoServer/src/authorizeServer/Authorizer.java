package authorizeServer;

import accountService.Account;
import base.Abonent;
import base.Address;
import base.MessageSystem;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;


public class Authorizer implements Abonent, Runnable {
    private final Address address = new Address();
    private final MessageSystem messageSystem;

    private final Map<String, ChannelHandlerContext> accountMap = new HashMap<>();

    public Authorizer(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.addService(this);
        messageSystem.getAddressService().register(this);
    }

    public void addAccount(Account acc, ChannelHandlerContext ctx) {
        accountMap.put(acc.getName(), ctx);
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public ChannelHandlerContext getContext(Account account) {
        return accountMap.get(account.getName());
    }

    public void removeContext(Account account) {
        accountMap.remove(account.getName());
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while (true) {
            messageSystem.execForAbonent(this);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
