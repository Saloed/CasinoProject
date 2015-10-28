package base;

import java.util.concurrent.atomic.AtomicInteger;


public final class Address {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
    private final int id;

    public Address() {
        id = ID_GENERATOR.incrementAndGet();
    }

    @Override
    public int hashCode() {
        return id;
    }
}
