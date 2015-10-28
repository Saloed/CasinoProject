package messageSystem;

import authorizeClient.AuthorizeClient;
import base.Address;
import base.AddressService;
import gameClient.GameClient;
import gameService.GameService;

public final class AddressServiceImpl implements AddressService {
    private Address gameClient;
    private Address authorizeClient;
    private Address gameService;

    public void register(Object object) {

        if (object instanceof GameClient)
            gameClient = ((GameClient) object).getAddress();
        else if (object instanceof AuthorizeClient)
            authorizeClient = ((AuthorizeClient) object).getAddress();
        else if (object instanceof GameService)
            gameService = ((GameService) object).getAddress();
    }

    public Address getGameClientAddress() {
        return gameClient;
    }

    public Address getAuthorizeClientAddress() {
        return authorizeClient;
    }

    public Address getGameServiceAddress() {
        return gameService;
    }
}
