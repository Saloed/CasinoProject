package base;


public interface MessageSystem {

    public AddressService getAddressService();

    public void addService(Abonent abonent);

    public void sendMessage(Message message);

    public void execForAbonent(Abonent abonent);
}
