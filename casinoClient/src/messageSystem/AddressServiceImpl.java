package messageSystem;

import authorizeClient.AuthorizeClient;
import base.Abonent;
import base.Address;
import base.AddressService;
import chatClient.ChatClient;
import frontend.FrontEnd;
import gameClient.GameClient;
import gameService.GameService;

public final class AddressServiceImpl implements AddressService {
    private Address gameClient;
    private Address authorizeClient;
    private Address gameService;
    private Address frontend;
    private Address chatClient;

    public void register(Abonent object) {

        if (object instanceof GameClient)
            gameClient = ((GameClient) object).getAddress();
        else if (object instanceof AuthorizeClient)
            authorizeClient = ((AuthorizeClient) object).getAddress();
        else if (object instanceof GameService)
            gameService = ((GameService) object).getAddress();
        else if (object instanceof FrontEnd)
            frontend = ((FrontEnd) object).getAddress();
        else if(object instanceof ChatClient)
            chatClient=((ChatClient)object).getAddress();
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

    public Address getFrontEndAddress() {
        return frontend;
    }

    public Address getChatClientAddress() {
        return chatClient;
    }
}
