package gameManager.messages;

import accountService.Account;
import base.Address;
import gameManager.GameManager;
import gameManager.Player;

public class MessageNewUserInCurrentSession extends MessageToGameManager {

    private final Account account;
    private final Integer sessionId;

    public MessageNewUserInCurrentSession(Address from, Address to, Integer sessionId, Account account) {
        super(from, to);
        this.account = account;
        this.sessionId = sessionId;
    }

    public void exec(GameManager gameManager) {
        gameManager.addNewActiveUser(sessionId, new Player(account, sessionId));
    }

}
