package base;


public interface AddressService {

    void register(Abonent object);

    Address getGameManagerAddress();

    Address getAccountServiceAddress();

    Address getAuthorizerAddress();
}
