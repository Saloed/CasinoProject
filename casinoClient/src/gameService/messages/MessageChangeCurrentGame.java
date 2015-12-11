package gameService.messages;

import base.Address;
import gameService.GameService;

public class MessageChangeCurrentGame extends MessageToGameService {

    private final GameService.GameType game;

    public MessageChangeCurrentGame(Address from, Address to, GameService.GameType newGame) {
        super(from, to);
        this.game = newGame;
    }

    public void exec(GameService gameService) {
        gameService.changeCurrentGame(game);
    }

}
