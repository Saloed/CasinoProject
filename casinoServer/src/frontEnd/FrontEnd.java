package frontEnd;

import accountService.Account;
import accountService.MessageAuthenticate;
import accountService.MessageRegister;
import base.Abonent;
import base.Address;
import base.Message;
import base.MessageSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by admin on 20.10.2015.
 */
public final class FrontEnd implements FrontEndService, Abonent, Runnable {

    private final Address address = new Address();
    private final MessageSystem messageSystem;

    private final Map<String, Boolean> waitingUsers = new HashMap<>();
    private final Map<String, Account> accountMap = new HashMap<>();

    public FrontEnd(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.addService(this);
        messageSystem.getAddressService().register(this);
    }

    @Override
    public void register(String name, String password) {
        final Message messageRegister = new MessageRegister(getAddress(),
                messageSystem.getAddressService().getAccountServiceAddress(), name, password);
        messageSystem.sendMessage(messageRegister);
    }

    @Override
    public boolean isRegistered(String name) {
        Boolean value = waitingUsers.get(name);
        return value != null && value;
    }
/*
    @Override
    public String authenticate(String name, String password) {
        final String sessionId = UUID.randomUUID().toString();
        Message messageAuthenticate = new MessageAuthenticate(getAddress(),
                messageSystem.getAddressService().getAccountServiceAddress(), name, password, sessionId);
        messageSystem.sendMessage(messageAuthenticate);
        return sessionId;
    }
*/
    @Override
    public boolean isAuthenticated(String sessionId) {
        return accountMap.containsKey(sessionId);
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


    void registered(String name, boolean result) {
        waitingUsers.put(name, result);
    }

    void authenticated(String sessionId, Account account) {
        accountMap.put(sessionId, account);

    }


}
