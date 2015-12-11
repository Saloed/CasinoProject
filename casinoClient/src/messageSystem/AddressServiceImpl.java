package messageSystem;

import authorizeClient.AuthorizeClient;
import base.Abonent;
import base.Address;
import base.AddressService;
import chatClient.ChatClient;
import frontend.AuthorizeController;
import frontend.MainWindowController;
import frontend.RouletteWindowController;
import frontend.SlotWindowController;
import gameClient.GameClient;
import gameService.GameService;

public final class AddressServiceImpl implements AddressService {
    private Address gameClient;
    private Address authorizeClient;
    private Address gameService;
    private Address chatClient;
    private Address authorizeController;
    private Address mainWindowController;
    private Address slotWindowController;
    private Address rouletteWindowController;

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
        else if (object instanceof SlotWindowController)
            slotWindowController = object.getAddress();
        else if (object instanceof RouletteWindowController)
            rouletteWindowController = object.getAddress();
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

    public Address getChatClientAddress() {
        return chatClient;
    }

    public Address getAuthorizeControllerAddress() {
        return authorizeController;
    }

    public Address getMainWindowControllerAddress() {
        return mainWindowController;
    }

    public Address getSlotWindowControllerAddress() {
        return slotWindowController;
    }

    public Address getRouletteWindowControllerAddress() {
        return rouletteWindowController;
    }

}
