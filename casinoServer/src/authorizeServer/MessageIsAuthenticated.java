package authorizeServer;

import accountService.Account;
import base.Abonent;
import base.Address;
import base.GameMessage.UserAuthorizeAnswerMessage;
import base.Message;
import gameManager.messages.MessageNewUserInCurrentSession;

import java.util.concurrent.atomic.AtomicInteger;


public class MessageIsAuthenticated extends Message {

    final Account account;

    public MessageIsAuthenticated(Address from, Address to, Account account) {
        super(from, to);
        this.account = account;
    }

    public void exec(Abonent abonent) {
        if (abonent instanceof Authorizer) {
            Authorizer authorizer = (Authorizer) abonent;
            UserAuthorizeAnswerMessage msg;

            if (account.getId() != 0) {
                Integer sessionId = new AtomicInteger().incrementAndGet();
                msg = UserAuthorizeAnswerMessage.newBuilder()
                        .setAnswer(true)
                        .setUserName(account.getName())
                        .setPassword(account.getPassword())
                        .setSessionId(sessionId)
                        .build();

                authorizer.addUserToCurrentSession(sessionId, account);
                Message message = new MessageNewUserInCurrentSession(authorizer.getAddress(),
                        authorizer.getMessageSystem().getAddressService().getGameManagerAddress(),
                        sessionId, account);
                authorizer.getMessageSystem().sendMessage(message);
            } else {
                msg = UserAuthorizeAnswerMessage.newBuilder()
                        .setAnswer(false)
                        .setUserName(account.getName())
                        .setPassword(account.getPassword())
                        .build();
            }

            authorizer.getContext(account).writeAndFlush(msg);

            

            authorizer.removeContext(account);
        }
    }
}
