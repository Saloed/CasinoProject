package gameService.messages;

import base.Address;
import base.GameMessage;
import base.GameMessage.Request;
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
        GameMessage.Request.Builder request = GameMessage.Request.newBuilder();
        request.setSessionId(gameService.getSessionId());
        request.setRequestType(Request.RequestType.GAME);
        GameMessage.Request.ServerRequest.Builder builder = GameMessage.Request.ServerRequest.newBuilder();
        GameMessage.Request.ServerRequest.GameType game =
                GameMessage.Request.ServerRequest.GameType.valueOf(gameService.getCurrentGame().toString());
        if (bet == null && betCash == null) {
            builder.setGame(game);
            gameService.changeCurrentGame(GameService.GameType.NO_GAME);
        } else if (bet == null) {
            builder.setGame(game);
            GameMessage.Request.ServerRequest.Bet.Builder betBuilder = GameMessage.Request.ServerRequest.Bet.newBuilder();
            betBuilder.setCash(betCash);
            builder.addBet(betBuilder.build());
        } else {
            builder.setGame(game);
            GameMessage.Request.ServerRequest.Bet.Builder betBuilder = GameMessage.Request.ServerRequest.Bet.newBuilder();
            betBuilder.setCash(betCash);
            betBuilder.setCoefficient(1);
            betBuilder.addBet(bet);
            builder.addBet(betBuilder.build());
        }
        request.setGameRequest(builder.build());
        gameService.sendRequest(request.build());
    }
}
