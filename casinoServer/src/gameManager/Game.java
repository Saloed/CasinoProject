package gameManager;


import base.MessageSystem;
import messageSystem.MessageSystemImpl;

public abstract class Game implements Runnable {
    public Integer sessionId;
    public MessageSystem messageSystem;

    public Game(MessageSystem messageSystem, Integer sessionId) {
        this.messageSystem = messageSystem;
        this.sessionId = sessionId;
    }

    @Override
    abstract public void run();
}
