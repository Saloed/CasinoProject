package gameService.messages;

import base.Address;
import base.GameMessage;
import gameService.GameService;

public class MessageSendRequest extends MessageToGameService {

    private final Integer bet;
    private final Integer betCash;


    /* betCash - money that bet
    * bet - for example: number in roulette which you choose.
    * if bet not needed set bet=null
    * if need to leave current game set bet=null and betCash=null
    * */
    public MessageSendRequest(Address from, Address to, Integer betCash, Integer bet) {
        super(from, to);
        this.bet = bet;
        this.betCash = betCash;
    }

    public void exec(GameService gameService) {
        GameMessage.ServerRequest.Builder builder = GameMessage.ServerRequest.newBuilder();
        GameMessage.ServerRequest.GameType game =
                GameMessage.ServerRequest.GameType.valueOf(gameService.getCurrentGame().toString());
        if (bet == null && betCash == null) {
            builder.setSessionId(gameService.getSessionId());
            builder.setGame(game);
            gameService.changeCurrentGame(GameService.GameType.NO_GAME);
        } else if (bet == null) {
            builder.setSessionId(gameService.getSessionId());
            builder.setGame(game);
            builder.addBet(betCash);
        } else {
            builder.setSessionId(gameService.getSessionId());
            builder.setGame(game);
            builder.addBet(betCash);
            builder.addBet(bet);
        }

        GameMessage.ServerRequest msg = builder.build();
        gameService.sendRequest(msg);
    }
}
