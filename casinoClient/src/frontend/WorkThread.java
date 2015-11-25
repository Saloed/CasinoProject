package frontend;

import base.Abonent;
import base.MessageSystem;

/**
 * Created by admin on 25.11.2015.
 */
class WorkThread implements Runnable {

    private final MessageSystem messageSystem;
    //private final Address address;
    private final Abonent instance;

    WorkThread(MessageSystem messageSystem, Abonent instance) {
        this.messageSystem = messageSystem;
        this.instance = instance;
    }


    @Override
    public void run() {
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
            System.err.println("Worker end work");
            if (interrupted)
                Thread.currentThread().interrupt();
        }

    }
}
