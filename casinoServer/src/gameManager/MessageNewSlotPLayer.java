package gameManager;

import base.Abonent;
import base.Address;
import base.Message;

/**
 * Created by user on 04.11.15.
 */
public class MessageNewSlotPLayer extends Message {
    private final Player player;

    public MessageNewSlotPLayer(Address from, Address to, Player player) {
        super(from, to);
        this.player = player;
    }

    public void exec(Abonent abonent) {
        if (abonent instanceof GameSlotMachine) {
            ((GameSlotMachine) abonent).addPlayer(player);
        }
    }
}
