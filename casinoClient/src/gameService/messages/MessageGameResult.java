package gameService.messages;

import base.Address;
import base.GameMessage;
import gameService.GameService;

/**
 * Created by admin on 29.10.2015.
 */
public class MessageGameResult extends MessageToGameService {
    private GameMessage.ServerAnswer msg;

    public MessageGameResult(Address from, Address to, GameMessage.ServerAnswer msg) {
        super(from, to);
        this.msg = msg;
    }

    //TODO rework (it just for test)
    public void exec(GameService gameService) {
        /*
        System.out.println("Answer: Cash=" + msg.getCash() +
                " Slots:" + msg.getGameData(0) + msg.getGameData(1) + msg.getGameData(2));
                */
        System.out.println("Answer: Cash="+msg.getCash()+" Roulette: "+msg.getGameData(0));
    }
}
