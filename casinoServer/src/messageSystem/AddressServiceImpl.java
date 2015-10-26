package messageSystem;

import accountService.AccountService;
import authorizeServer.Authorizer;
import base.Address;
import base.AddressService;
import frontEnd.FrontEnd;
import gameManager.GameManager;

public final class AddressServiceImpl implements AddressService {
    private Address frontEnd;
    private Address gameManager;
    private Address accountService;
    private Address authorizer;
/*
    public void register(FrontEnd frontEnd) {
        this.frontEnd = frontEnd.getAddress();
    }

    public void register(GameManager gameManager) {
        this.gameManager = gameManager.getAddress();
    }

    public void register(AccountService accountService) {
        this.accountService = accountService.getAddress();
    }
    */

    public void register(Object object) {

        if (object instanceof FrontEnd)
            frontEnd = ((FrontEnd) object).getAddress();
        else if (object instanceof GameManager)
            gameManager = ((GameManager) object).getAddress();
        else if (object instanceof AccountService)
            accountService = ((AccountService) object).getAddress();
        else if (object instanceof Authorizer)
            authorizer = ((Authorizer) object).getAddress();

    }

    public Address getFrontEndAddress() {
        return frontEnd;
    }

    public Address getGameManagerAddress() {
        return gameManager;
    }

    public Address getAccountServiceAddress() {
        return accountService;
    }

    public Address getAuthorizerAddress() {
        return authorizer;
    }

}
