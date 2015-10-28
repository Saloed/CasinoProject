package gameService;

import base.Abonent;
import base.Address;
import base.Message;

/**
 * Created by admin on 28.10.2015.
 */
public abstract class MessageToGameService extends Message {


    public MessageToGameService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public final void exec(Abonent abonent) {
        if (abonent instanceof GameService) {
            exec((GameService) abonent);
        }
    }

    protected abstract void exec(GameService service);
}
