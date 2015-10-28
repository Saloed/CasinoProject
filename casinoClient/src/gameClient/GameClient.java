package gameClient;

import base.Abonent;
import base.Address;
import messageSystem.MessageSystemImpl;

/**
 * Created by admin on 28.10.2015.
 */
public class GameClient implements Runnable, Abonent {
    private final Address address = new Address();
    private final MessageSystemImpl messageSystem;

    public GameClient(MessageSystemImpl messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
    }

    public Address getAddress() {
        return address;
    }


    public void run() {
    }
}
