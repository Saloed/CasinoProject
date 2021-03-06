package authorizeServer;

import accountService.Account;
import base.Abonent;
import base.Address;
import base.MessageSystem;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Authorizer implements Abonent, Runnable {
    private final Address address = new Address();
    private final MessageSystem messageSystem;

    private final Map<String, ChannelHandlerContext> accountMap = new HashMap<>();

    private final Map<Integer, Account> currentSession = new HashMap<>();

    public Authorizer(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.addService(this);
        messageSystem.getAddressService().register(this);
    }

    public void addUserToCurrentSession(Integer sessionId, Account account) {
        currentSession.put(sessionId, account);
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

    public boolean checkAccount(Account account) {
        Set<Integer> keys = currentSession.keySet();
        for (Integer key : keys) {
            if (currentSession.get(key).getName().equals(account.getName()))
                return false;
        }
        return true;
    }

    public void removeUserFromSession(Integer sessionId) {
        currentSession.remove(sessionId);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        try {
            while (true) {
                messageSystem.execForAbonent(this);

                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            // e.printStackTrace();
            Thread.currentThread().interrupt();
            System.err.println("Authorizer thread was interrupted");
        }

    }

}
