package gameManager;

import base.Abonent;
import base.Address;
import base.GameMessage;
import base.Message;
import gameManager.gameMessageSystem.GameMessageSystem;
import io.netty.channel.ChannelHandlerContext;
import messageSystem.MessageSystemImpl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public final class GameManager implements Abonent, Runnable {

    private final Address address = new Address();
    private final MessageSystemImpl messageSystemImpl;
    private final GameMessageSystem gameMessageSystem = new GameMessageSystem();
    private final Map<Integer, Player> activeUsers = new HashMap<>();
    private final Map<Integer, GameType> activePlayers = new HashMap<>();
    private final Map<Integer, ChannelHandlerContext> usersChannels = new HashMap<>();
    private final LinkedList<Thread> gameThreadList = new LinkedList<>();
    private final Map<GameType, Address> gameAdresses = new HashMap<>();

    public GameManager(MessageSystemImpl messageSystemImpl) {
        this.messageSystemImpl = messageSystemImpl;
        messageSystemImpl.addService(this);
        messageSystemImpl.getAddressService().register(this);
        gameMessageSystem.addService(this);
        gameMessageSystem.getAddressService().register(this);

        Thread slotMachineThread = new Thread(new GameSlotMachine(gameMessageSystem));
        Thread rouletteThread = new Thread(new GameRoulette(gameMessageSystem));

        gameThreadList.add(slotMachineThread);
        gameThreadList.add(rouletteThread);

        slotMachineThread.start();
        rouletteThread.start();

        gameAdresses.put(GameType.SLOT, gameMessageSystem.getAddressService().getSlotMachineAddress());
        gameAdresses.put(GameType.ROULETTE, gameMessageSystem.getAddressService().getRouletteAddress());

    }


//TODO add player change game functionality


    //TODO check and rework
    public void addNewPlayer(GameMessage.ServerRequest.GameType gameType, Integer sessionId, Integer bet) {
        GameType parsedGameType = GameType.valueOf(gameType.getDescriptorForType().getContainingType().getName());

        activePlayers.put(sessionId, parsedGameType);

        Address target = gameAdresses.get(parsedGameType);

        activeUsers.get(sessionId).changeBet(bet);
        Message msg = new MessageNewSlotPLayer(address, target,
                getPlayer(sessionId));
        gameMessageSystem.sendMessage(msg);

    }

    public GameMessageSystem getGameMessageSystem() {
        return gameMessageSystem;
    }

    public ChannelHandlerContext getUserChannel(Integer sessionId) {
        return usersChannels.get(sessionId);
    }

    public void changeAccountCash(Integer sessionId, Integer cash) {
        activeUsers.get(sessionId).getAccount().setCash(cash);
    }

    public void removeGame(Integer sessionId) {
        activePlayers.remove(sessionId);
    }

    public Player getPlayer(Integer sessionId) {
        return activeUsers.get(sessionId);
    }

    public void addNewActiveUser(Integer sessionId, Player player) {
        activeUsers.put(sessionId, player);
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

            gameMessageSystem.execForAbonent(this);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private enum GameType {

        SLOT,
        ROULETTE;

    }
}
