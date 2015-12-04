package authorizeServer.messages;

import accountService.Account;
import authorizeServer.Authorizer;
import base.Abonent;
import base.Address;
import base.GameMessage.UserAuthorizeAnswerMessage;
import base.Message;
import gameManager.messages.MessageNewUserInCurrentSession;


public class MessageIsAuthenticated extends Message {

    private final Account account;

    public MessageIsAuthenticated(Address from, Address to, Account account) {
        super(from, to);
        this.account = account;
    }

    public void exec(Abonent abonent) {
        if (abonent instanceof Authorizer) {
            Authorizer authorizer = (Authorizer) abonent;
            UserAuthorizeAnswerMessage msg;

            if (account.getId() != 0) {
                //Integer sessionId = new AtomicInteger().incrementAndGet();
                msg = UserAuthorizeAnswerMessage.newBuilder()
                        .setAnswer(true)
                        .setUserName(account.getName())
                        .setPassword(account.getPassword())
                        .setCash(account.getCash())
                        .setSessionId(account.getId())
                        .build();

                authorizer.addUserToCurrentSession(account.getId(), account);
                Message message = new MessageNewUserInCurrentSession(authorizer.getAddress(),
                        authorizer.getMessageSystem().getAddressService().getGameManagerAddress(),
                        account.getId(), account);
                authorizer.getMessageSystem().sendMessage(message);
            } else {
                msg = UserAuthorizeAnswerMessage.newBuilder()
                        .setAnswer(false)
                        .setUserName(account.getName())
                        .setPassword(account.getPassword())
                        .build();
            }
            try {
                authorizer.getContext(account).writeAndFlush(msg).sync();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            authorizer.removeContext(account);
        }
    }
}
