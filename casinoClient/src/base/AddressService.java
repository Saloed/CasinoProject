package base;


public interface AddressService {

    public void register(Object object);

    public Address getGameClientAddress();

    public Address getAuthorizeClientAddress();

    public Address getGameServiceAddress();

    public Address getFrontEndAddress();
}
