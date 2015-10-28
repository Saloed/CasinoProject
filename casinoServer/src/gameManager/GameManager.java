package gameManager;

import accountService.Account;
import base.Abonent;
import base.Address;
import messageSystem.MessageSystemImpl;

import java.util.HashMap;
import java.util.Map;


public final class GameManager implements Abonent, Runnable {
    private final Address address = new Address();
    private final MessageSystemImpl messageSystemImpl;

    public GameManager(MessageSystemImpl messageSystemImpl) {
        this.messageSystemImpl = messageSystemImpl;
        messageSystemImpl.addService(this);
        messageSystemImpl.getAddressService().register(this);
    }

    private final Map<Integer, Account> activeUsers = new HashMap<>();
    private final Map<Integer, Game> activePlayers = new HashMap<>();


    public void addNewActiveUser(Integer sessionId, Account account) {
        activeUsers.put(sessionId, account);
    }

    public MessageSystemImpl getMessageSystem() {
        return messageSystemImpl;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while (true) {
            messageSystemImpl.execForAbonent(this);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
