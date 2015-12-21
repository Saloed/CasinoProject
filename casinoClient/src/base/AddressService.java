package base;


public interface AddressService {

    void register(Abonent object);

    Address getAuthorizeClientAddress();

    Address getGameServiceAddress();

    //Address getChatClientAddress();

    Address getAuthorizeControllerAddress();

    Address getMainWindowControllerAddress();

    Address getSlotWindowControllerAddress();

    Address getRouletteWindowControllerAddress();
}
