package accountService;

import java.util.concurrent.atomic.AtomicInteger;


public final class Account {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

    private final int id;
    private final String name;
    private final String password;
    private int cash;

    public Account(String name, String password) {
        int id = ID_GENERATOR.getAndIncrement();
        while (id == 0)
            id = ID_GENERATOR.getAndIncrement();
        this.id = id;
        this.name = name;
        this.password = password;
        this.cash = 0;
    }

    public Account(int id, String name, String pass, int cash) {
        this.id = id;
        this.name = name;
        this.password = pass;
        this.cash = cash;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    @Override
    public int hashCode() {
        int hash = 19;
        hash = hash * id;
        hash = hash * cash;
        hash = hash * name.hashCode();
        hash = hash * password.hashCode();
        return hash;
    }
}
