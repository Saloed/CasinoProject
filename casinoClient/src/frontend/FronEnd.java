package frontend;

import base.Abonent;
import base.Address;
import messageSystem.MessageSystemImpl;

/**
 * Created by admin on 29.10.2015.
 */
public class FronEnd implements Runnable, Abonent {
    private final Address address = new Address();
    private final MessageSystemImpl messageSystem;

    public FronEnd(MessageSystemImpl messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
    }

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
