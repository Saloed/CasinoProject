package base;


public interface AddressService {

    void register(Abonent object);

    Address getGameClientAddress();

    Address getAuthorizeClientAddress();

    Address getGameServiceAddress();

    Address getFrontEndAddress();

    Address getChatClientAddress();

    Address getAuthorizeControllerAddress();

    Address getMainWindowControllerAdress();
}
