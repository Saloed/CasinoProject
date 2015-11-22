package base;


public interface MessageSystem {

    AddressService getAddressService();

    void addService(Abonent abonent);

    void sendMessage(Message message);

    void execForAbonent(Abonent abonent);
}
