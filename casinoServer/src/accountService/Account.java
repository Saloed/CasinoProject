package accountService;

import java.util.concurrent.atomic.AtomicInteger;


public final class Account {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

    private final int id;
    private final String name;
    private final String password;

    public Account(String name, String password) {
        int id = ID_GENERATOR.getAndIncrement();
        while (id == 0)
            id = ID_GENERATOR.getAndIncrement();
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Account(int id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.password = pass;
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

}
