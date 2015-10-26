package gameManager;

import base.Abonent;
import base.Address;
import base.Message;


public abstract class MessageToGameManager extends Message {

    public MessageToGameManager(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof GameManager) {
            exec((GameManager) abonent);
        }
    }

    protected abstract void exec(GameManager gameMechanics);
}
