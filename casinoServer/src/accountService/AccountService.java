package accountService;

import base.Abonent;
import base.Address;
import messageSystem.MessageSystemImpl;


public final class AccountService implements Abonent, Runnable {

    private final Address address = new Address();
    private final MessageSystemImpl messageSystemImpl;
    private final DataBase dataBase;

    public AccountService(MessageSystemImpl messageSystemImpl) {
        this.messageSystemImpl = messageSystemImpl;
        messageSystemImpl.addService(this);
        messageSystemImpl.getAddressService().register(this);

        this.dataBase = new DataBase();
    }

    public MessageSystemImpl getMessageSystem() {
        return messageSystemImpl;
    }

    public DataBase getAccountDataBase() {
        return dataBase;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        try {
            while (true) {
                messageSystemImpl.execForAbonent(this);
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            // e.printStackTrace();
            Thread.currentThread().interrupt();
            System.err.println("AccountService thread was interrupted");
        }
    }
}
