package base;


public interface AddressService {

    public void register(Abonent object);

    public Address getGameClientAddress();

    public Address getAuthorizeClientAddress();

    public Address getGameServiceAddress();

    public Address getFrontEndAddress();

    public Address getChatClientAddress();

    public Address getAuthorizeControllerAddress();
}
