package gameManager;

import accountService.Account;
import base.Abonent;
import base.Address;
import base.GameMessage;
import io.netty.channel.ChannelHandlerContext;
import messageSystem.MessageSystemImpl;

import java.util.HashMap;
import java.util.Map;


public final class GameManager implements Abonent, Runnable {
    private final Address address = new Address();
    private final MessageSystemImpl messageSystemImpl;

    public GameManager(MessageSystemImpl messageSystemImpl) {
        this.messageSystemImpl = messageSystemImpl;
        messageSystemImpl.addService(this);
        messageSystemImpl.getAddressService().register(this);
    }

    private final Map<Integer, Account> activeUsers = new HashMap<>();
    private final Map<Integer, Thread> activePlayers = new HashMap<>();//TODO rework this idea
    private final Map<Integer, ChannelHandlerContext> usersChannels = new HashMap<>();

    public void addNewGame(GameMessage.ServerRequest.GameType gameType, Integer sessionId) {
        Game newGame = null;
        switch (gameType) {
            case SLOT: {
                newGame = new GameSlotMachine(messageSystemImpl, sessionId, activeUsers.get(sessionId));
                break;
            }
        }
        Thread newGameThread = new Thread(newGame);
        activePlayers.put(sessionId, newGameThread);
        newGameThread.start();
    }

    public ChannelHandlerContext getUserChannel(Integer sessionId) {
        return usersChannels.get(sessionId);
    }

    public void changeAccountCash(Integer sessionId, Integer cash) {
        activeUsers.get(sessionId).setCash(cash);
    }

    public void removeGame(Integer sessionId) {
        activePlayers.remove(sessionId);
    }

    public Account getPlayerAccount(Integer sessionId) {
        return activeUsers.get(sessionId);
    }

    public void addNewActiveUser(Integer sessionId, Account account) {
        activeUsers.put(sessionId, account);
    }

    public void addUserChannel(Integer sessionId, ChannelHandlerContext ctx) {
        usersChannels.put(sessionId, ctx);
    }

    public void removeUserChannel(Integer sessionId) {
        usersChannels.remove(sessionId);
    }


    public MessageSystemImpl getMessageSystem() {
        return messageSystemImpl;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while (true) {
            messageSystemImpl.execForAbonent(this);


            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
