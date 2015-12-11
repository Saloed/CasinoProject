package gameService.messages;

import base.Address;
import base.GameMessage;
import base.Message;
import frontend.messages.MessageToRouletteWindowController;
import frontend.messages.MessageToSlotWindowController;
import gameService.GameService;

public class MessageGameResult extends MessageToGameService {
    private final GameMessage.ServerAnswer msg;

    public MessageGameResult(Address from, Address to, GameMessage.ServerAnswer msg) {
        super(from, to);
        this.msg = msg;
    }

    public void exec(GameService gameService) {
        /*
        System.out.println("Answer: Cash=" + msg.getCash() +
                " Slots:" + msg.getGameData(0) + msg.getGameData(1) + msg.getGameData(2));

        System.out.println("Answer: Cash="+msg.getCash()+" Roulette: "+msg.getGameData(0)); */

        if (gameService.getCurrentGame().equals(GameService.GameType.SLOT)) {
            Message message = new MessageToSlotWindowController(gameService.getAddress(),
                    gameService.getMessageSystem().getAddressService().getSlotWindowControllerAddress(),
                    msg.getGameData(0), msg.getGameData(1), msg.getGameData(2), msg.getCash());
            gameService.getMessageSystem().sendMessage(message);
        } else if (gameService.getCurrentGame().equals(GameService.GameType.ROULETTE)) {
            Message message = new MessageToRouletteWindowController(gameService.getAddress(),
                    gameService.getMessageSystem().getAddressService().getRouletteWindowControllerAddress(),
                    msg.getCash(), msg.getGameData(0));
            gameService.getMessageSystem().sendMessage(message);
        }

    }
}
