package frontend;

import base.Abonent;
import base.Address;
import javafx.application.Application;
import messageSystem.MessageSystemImpl;


public class FrontEnd implements Runnable, Abonent {
  private final Address address = new Address();
    private final MessageSystemImpl messageSystem;

    public FrontEnd(MessageSystemImpl messageSystem) {
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
