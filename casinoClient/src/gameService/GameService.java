package gameService;

import base.Abonent;
import base.Address;
import messageSystem.MessageSystemImpl;

/**
 * Created by admin on 28.10.2015.
 */
public class GameService implements Runnable, Abonent {
    private final Address address = new Address();
    private final MessageSystemImpl messageSystem;
    private Integer sessionId = 0;

    public GameService(MessageSystemImpl messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
    }

    public void changeSessionId(Integer sessionId) {
        this.sessionId = sessionId;
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
