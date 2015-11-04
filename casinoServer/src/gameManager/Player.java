package gameManager;

import accountService.Account;

/**
 * Created by user on 04.11.15.
 */
public class Player {
    private Account account;
    private Integer sessioId;
    private Integer bet;
    private Integer betCash;

    public Player(Account account, Integer sessioId, Integer bet) {
        this.account = account;
        this.bet = bet;
        this.sessioId = sessioId;
    }

    public Player(Account account, Integer sessioId) {
        this.account = account;
        this.sessioId = sessioId;
        this.bet = null;
    }

    public Account getAccount() {
        return account;
    }

    public Integer getSessioId() {
        return sessioId;
    }

    public Integer getBet() {
        return bet;
    }

    public Integer getBetCash() {
        return betCash;
    }

    public void changeBet(Integer bet, Integer betCash) {
        this.bet = bet;
        this.betCash = betCash;
    }
}
