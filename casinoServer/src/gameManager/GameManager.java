package gameManager;

import base.Abonent;
import base.Address;
import base.GameMessage;
import base.Message;
import gameManager.gameMessageSystem.GameMessageSystem;
import gameManager.messages.MessageNewPLayer;
import io.netty.channel.ChannelHandlerContext;
import messageSystem.MessageSystemImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public final class GameManager implements Abonent, Runnable {

    private final Address address = new Address();
    private final MessageSystemImpl messageSystemImpl;
    private final GameMessageSystem gameMessageSystem = new GameMessageSystem();
    private final Map<Integer, Player> activeUsers = new HashMap<>();
    private final Map<Integer, GameType> activePlayers = new HashMap<>();
    private final Map<Integer, ChannelHandlerContext> usersChannels = new HashMap<>();
    // private final LinkedList<Thread> gameThreadList = new LinkedList<>();
    private final ExecutorService gameThreadPool = Executors.newFixedThreadPool(2);
    private final Map<GameType, Address> gameAdresses = new HashMap<>();
    private boolean interrupted = false;


    public GameManager(MessageSystemImpl messageSystemImpl) {
        this.messageSystemImpl = messageSystemImpl;
        messageSystemImpl.addService(this);
        messageSystemImpl.getAddressService().register(this);
        gameMessageSystem.addService(this);
        gameMessageSystem.getAddressService().register(this);

        gameThreadPool.execute(new GameSlotMachine(gameMessageSystem));
        gameThreadPool.execute(new GameRoulette(gameMessageSystem));
/*
        Thread slotMachineThread = new Thread(new GameSlotMachine(gameMessageSystem));
        Thread rouletteThread = new Thread(new GameRoulette(gameMessageSystem));

        gameThreadList.add(slotMachineThread);
        gameThreadList.add(rouletteThread);

        slotMachineThread.start();
        rouletteThread.start();
*/
        gameAdresses.put(GameType.SLOT, gameMessageSystem.getAddressService().getSlotMachineAddress());
        gameAdresses.put(GameType.ROULETTE, gameMessageSystem.getAddressService().getRouletteAddress());

    }

    //TODO check and rework
    public void addNewPlayer(GameMessage.ServerRequest.GameType gameType, Integer sessionId, Integer betCash, Integer bet) {
        GameType parsedGameType = GameType.valueOf(gameType.toString());

        if (GameType.NO_GAME.equals(parsedGameType)) {
            if (betCash != null) {
                changeAccountCash(sessionId, activeUsers.get(sessionId).getAccount().getCash() + betCash);
                return;
            }
        }

        if (!activePlayers.containsKey(sessionId)) {
            if (betCash != null) {
                activePlayers.put(sessionId, parsedGameType);

                Address target = gameAdresses.get(parsedGameType);

                activeUsers.get(sessionId).changeBet(bet, betCash);
                Message msg = new MessageNewPLayer(address, target,
                        getPlayer(sessionId));
                gameMessageSystem.sendMessage(msg);
            }
        } else {
            if (activePlayers.get(sessionId).equals(parsedGameType)) {
                if (betCash != null) {
                    Address target = gameAdresses.get(parsedGameType);

                    activeUsers.get(sessionId).changeBet(bet, betCash);
                    Message msg = new MessageNewPLayer(address, target,
                            getPlayer(sessionId));
                    gameMessageSystem.sendMessage(msg);
                } else {
                    activePlayers.replace(sessionId, GameType.NO_GAME);
                }
            } else {
                if (betCash != null) {
                    Address target = gameAdresses.get(parsedGameType);
                    activePlayers.replace(sessionId, parsedGameType);
                    activeUsers.get(sessionId).changeBet(bet, betCash);
                    Message msg = new MessageNewPLayer(address, target,
                            getPlayer(sessionId));
                    gameMessageSystem.sendMessage(msg);
                } else {
                    activePlayers.replace(sessionId, GameType.NO_GAME);
                }
            }
        }
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

    public void removeUser(Integer sessionId) {
        activeUsers.remove(sessionId);
    }

    //TODO be care  with this function!!!!!
    public Integer getPlayerSessionByContext(ChannelHandlerContext ctx) {
        if (!usersChannels.containsValue(ctx))
            return null;
        Set<Integer> keySet = usersChannels.keySet();
        for (Integer key : keySet) {
            //if ((usersChannels.get(key).channel().remoteAddress()).equals(ctx.channel().remoteAddress()))
            if (usersChannels.get(key).equals(ctx))
                return key;
        }
        return null;
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
        if (!usersChannels.containsKey(sessionId)) {
            usersChannels.put(sessionId, ctx);
        }
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

        try {
            while (true) {
                messageSystemImpl.execForAbonent(this);

                gameMessageSystem.execForAbonent(this);

                Thread.sleep(10);
            }
        } catch (InterruptedException consumed) {
            // e.printStackTrace();
            interrupted = true;
            //Thread.currentThread().interrupt();
            System.err.println("GameManager thread was interrupted");
        } finally {

            try {
                gameThreadPool.shutdown();
                if (!gameThreadPool.awaitTermination(1, TimeUnit.SECONDS)) {
                    gameThreadPool.shutdownNow();
                    if (!gameThreadPool.awaitTermination(1, TimeUnit.SECONDS))
                        System.err.println("Game thread pool did not terminate");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("Error when shutdown game thread pool");
            } finally {
                if (interrupted)
                    Thread.currentThread().interrupt();
                //System.err.println("GameManager thread was interrupted");

            }

        }

    }

    private enum GameType {
        SLOT,
        ROULETTE,
        NO_GAME

    }
}
