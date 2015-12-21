package frontend;

import base.Abonent;
import base.MessageSystem;

public class WorkThread implements Runnable {

    private final MessageSystem messageSystem;
    //private final Address address;
    private final Abonent instance;
    private String name = "";

    public WorkThread(MessageSystem messageSystem, Abonent instance, String name) {
        this.messageSystem = messageSystem;
        this.instance = instance;
        this.name = name;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(name);
        Boolean interrupted = false;
        try {
            while (true) {
                messageSystem.execForAbonent(instance);


                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            System.err.println("Worker thread was interrupted");
            interrupted = true;
        } finally {
            System.err.println("Worker " + name + " end work");
            if (interrupted)
                Thread.currentThread().interrupt();
        }

    }
}
