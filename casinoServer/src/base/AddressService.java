package base;


public interface AddressService {

    public void register(Abonent object);

    public Address getGameManagerAddress();

    public Address getAccountServiceAddress();

    public Address getAuthorizerAddress();
}
