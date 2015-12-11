package gameManager.gameMessageSystem;

import base.Abonent;
import base.Address;
import base.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameMessageSystem {


    private final Map<Address, ConcurrentLinkedQueue<Message>> messages = new HashMap<>();
    private final GameAddressService addressService = new GameAddressService();

    public GameMessageSystem() {
    }

    public GameAddressService getAddressService() {
        return addressService;
    }

    public void addService(Abonent abonent) {
        messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<>());
    }

    public void sendMessage(Message message) {
        (messages.get(message.getTo())).add(message);

    }

    public void execForAbonent(Abonent abonent) {
        ConcurrentLinkedQueue<Message> queue = messages.get(abonent.getAddress());
        while (!queue.isEmpty()) {
            Message message = queue.poll();
            message.exec(abonent);
        }
    }

}


