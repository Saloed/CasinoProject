package gameManager;

import base.Abonent;
import base.Address;
import messageSystem.MessageSystemImpl;


public final class GameManager implements Abonent, Runnable {
    private final Address address = new Address();
    private final MessageSystemImpl messageSystemImpl;

    public GameManager(MessageSystemImpl messageSystemImpl) {
        this.messageSystemImpl = messageSystemImpl;
        messageSystemImpl.addService(this);
        messageSystemImpl.getAddressService().register(this);
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
