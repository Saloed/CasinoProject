package authorizeServer;

import accountService.Account;
import accountService.messages.MessageAuthenticate;
import accountService.messages.MessageRegister;
import base.Abonent;
import base.Address;
import base.GameMessage.UserAuthorizeMessage;
import base.Message;
import io.netty.channel.ChannelHandlerContext;


public class MessageToAuthorizer extends Message {

    private final ChannelHandlerContext ctx;
    private final UserAuthorizeMessage msg;

    public MessageToAuthorizer(Address from, Address to, ChannelHandlerContext ctx, UserAuthorizeMessage msg) {
        super(from, to);
        this.ctx = ctx;
        this.msg = msg;

    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof Authorizer) {
            Authorizer authorizer = (Authorizer) abonent;

            Account account = new Account(msg.getUserName(), msg.getPassword());
            authorizer.addAccount(account, ctx);
            if (!msg.getRegister()) {
                Message mes = new MessageAuthenticate(authorizer.getAddress(),
                        authorizer.getMessageSystem().getAddressService().getAccountServiceAddress(),
                        account.getName(), account.getPassword());
                authorizer.getMessageSystem().sendMessage(mes);
            } else {
                Message message = new MessageRegister(authorizer.getAddress(),
                        authorizer.getMessageSystem().getAddressService().getAccountServiceAddress(),
                        account.getName(), account.getPassword());
                authorizer.getMessageSystem().sendMessage(message);
            }
        }
    }

}
