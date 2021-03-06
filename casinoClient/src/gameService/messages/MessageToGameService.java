package gameService.messages;

import base.Abonent;
import base.Address;
import base.Message;
import gameService.GameService;

public abstract class MessageToGameService extends Message {


    MessageToGameService(Address from, Address to) {
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
