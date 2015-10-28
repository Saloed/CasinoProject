package messageSystem;

import base.Abonent;
import base.Address;
import base.Message;
import base.MessageSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;


public final class MessageSystemImpl implements MessageSystem {

    private final Map<Address, ConcurrentLinkedQueue<Message>> messages = new HashMap<>();
    private final AddressServiceImpl addressService = new AddressServiceImpl();

    public MessageSystemImpl() {
    }

    public AddressServiceImpl getAddressService() {
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
