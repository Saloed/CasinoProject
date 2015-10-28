package base;


public interface AddressService {

    public void register(Object object);

    public Address getGameManagerAddress();

    public Address getAccountServiceAddress();

    public Address getAuthorizerAddress();
}
