package messageSystem;

import authorizeClient.AuthorizeClient;
import base.Abonent;
import base.Address;
import base.AddressService;
import chatClient.ChatClient;
import frontend.AuthorizeController;
import frontend.MainWindowController;
import gameClient.GameClient;
import gameService.GameService;

public final class AddressServiceImpl implements AddressService {
    private Address gameClient;
    private Address authorizeClient;
    private Address gameService;
    private Address frontend;
    private Address chatClient;
    private Address authorizeController;
    private Address mainWindowController;

    public void register(Abonent object) {

        if (object instanceof GameClient)
            gameClient = (object).getAddress();
        else if (object instanceof AuthorizeClient)
            authorizeClient = (object).getAddress();
        else if (object instanceof GameService)
            gameService = (object).getAddress();
        else if (object instanceof ChatClient)
            chatClient = (object).getAddress();
        else if (object instanceof AuthorizeController)
            authorizeController = (object).getAddress();
        else if (object instanceof MainWindowController)
            mainWindowController = object.getAddress();
      /*
            else if (object instanceof FrontEnd)
            frontend = (object).getAddress();
            */
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

    public Address getAuthorizeControllerAddress() {
        return authorizeController;
    }


    public Address getMainWindowControllerAdress() {
        return mainWindowController;
    }
}
