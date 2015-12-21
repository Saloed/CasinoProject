package gameManager;

import accountService.Account;
import base.GameMessage;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private final Account account;
    private final Integer sessionId;
    private List<GameMessage.Request.ServerRequest.Bet> bet;


    public Player(Account account, Integer sessionId) {
        this.account = account;
        this.sessionId = sessionId;
        this.bet = new ArrayList<>();
    }

    public Account getAccount() {
        return account;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public List<GameMessage.Request.ServerRequest.Bet> getBet() {
        return bet;
    }


    public void changeBet(List<GameMessage.Request.ServerRequest.Bet> bet) {
        this.bet = bet;
    }

    @Override
    public int hashCode() {
        int result = account.hashCode();
        result = 31 * result + sessionId.hashCode();
        result = 31 * result + bet.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Player player = (Player) object;

        return account != null ?
                account.equals(player.account) : player.account == null && (sessionId != null ?
                sessionId.equals(player.sessionId) : player.sessionId == null && (bet != null ?
                bet.equals(player.bet) : player.bet == null));

    }

}
