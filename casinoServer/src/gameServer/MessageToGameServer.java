package gameServer;

import base.Abonent;
import base.Address;
import base.Message;
import gameManager.GameManager;


public abstract class MessageToGameServer extends Message {
    public MessageToGameServer(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof GameServerHandler) {
            exec((GameManager) abonent);
        }
    }

    protected abstract void exec(GameServerHandler gameServerHandler);

}
