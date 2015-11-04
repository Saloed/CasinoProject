package gameManager;

import accountService.Account;


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

    @Override
    public boolean equals(Object object) {
        if (object instanceof Player) {
            Player other = (Player) object;
            if (account == null && sessioId == null && betCash == null && bet == null) {
                if (other.account == null && other.sessioId == null && other.bet == null && other.betCash == null)
                    return true;
            }
            assert account != null;
            if (account.equals(other.account) && sessioId.equals(other.sessioId)) {
                if (bet == null && betCash == null) {
                    if (other.bet == null && other.betCash == null)
                        return true;
                } else {
                    if (bet == null && other.bet == null) {
                        if (betCash.equals(other.betCash))
                            return true;
                    } else if (betCash == null && other.betCash == null) {
                        if (bet.equals(other.bet))
                            return true;
                    } else {
                        assert bet != null;
                        assert betCash != null;
                        if (bet.equals(other.bet) && betCash.equals(other.betCash))
                            return true;
                    }
                }

            }

        }

        return false;
    }
}
