package authorizeClient.messages;

import authorizeClient.AuthorizeClient;
import base.Address;
import base.GameMessage;


public class MessageAuthorizeUser extends MessageToAuthorizeClient {

    private String userName;
    private String password;
    private boolean register;


    public MessageAuthorizeUser(Address from, Address to, String userName, String password, boolean register) {
        super(from, to);
        this.password = password;
        this.register = register;
        this.userName = userName;
    }

    public void exec(AuthorizeClient authorizeClient) {
        GameMessage.UserAuthorizeMessage msg = GameMessage.UserAuthorizeMessage.newBuilder()
                .setUserName(userName)
                .setPassword(password)
                .setRegister(register)
                .build();
        authorizeClient.reconnect();
        if (authorizeClient.readyToWork()) {
            authorizeClient.sendRequest(msg);
        } else {
            authorizeClient.notifyConnectionError();
        }
    }
}
