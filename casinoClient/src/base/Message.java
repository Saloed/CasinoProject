package base;


public abstract class Message {
    private final Address from;
    private final Address to;

    protected Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getTo() {
        return to;
    }

    public Address getFrom() {
        return from;
    }

    public abstract void exec(Abonent abonent);
}
