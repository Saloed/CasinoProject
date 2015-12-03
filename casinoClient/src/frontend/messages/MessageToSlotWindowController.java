package frontend.messages;

import base.Abonent;
import base.Address;
import base.Message;
import frontend.SlotWindowController;

/**
 * Created by FedoR on 29.11.2015.
 */
public class MessageToSlotWindowController extends Message {

    private int first;
    private int second;
    private int third;
    private int resultCash;

    public MessageToSlotWindowController(Address from, Address to, int first, int second, int third, int resultCash) {
        super(from, to);
        this.first = first;
        this.second = second;
        this.third = third;
        this.resultCash = resultCash;
    }

    @Override
    public void exec(Abonent abonent) {

        if (abonent instanceof SlotWindowController) {
            SlotWindowController get = (SlotWindowController) abonent;
            get.keepRolling(first, second, third, resultCash);

        }

    }
}
