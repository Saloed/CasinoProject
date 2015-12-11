package gameManager.messages;

import base.Abonent;
import base.Address;
import base.Message;
import gameManager.GameRoulette;
import gameManager.GameSlotMachine;
import gameManager.Player;

public class MessageNewPLayer extends Message {
    private final Player player;

    public MessageNewPLayer(Address from, Address to, Player player) {
        super(from, to);
        this.player = player;
    }

    public void exec(Abonent abonent) {
        if (abonent instanceof GameSlotMachine) {
            ((GameSlotMachine) abonent).addPlayer(player);
        } else if (abonent instanceof GameRoulette) {
            ((GameRoulette) abonent).addPlayer(player);
        }

    }
}
