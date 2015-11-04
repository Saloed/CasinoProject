package gameManager;


import base.Abonent;
import base.Address;
import gameManager.gameMessageSystem.GameMessageSystem;

public abstract class Game implements Runnable, Abonent {
    public GameMessageSystem messageSystem;
    Address address = new Address();

    public Game(GameMessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.addService(this);
        messageSystem.getAddressService().register(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    abstract public void run();
}
