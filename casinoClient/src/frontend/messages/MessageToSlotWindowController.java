package frontend.messages;

import base.Abonent;
import base.Address;
import base.Message;
import frontend.SlotWindowController;

public class MessageToSlotWindowController extends Message {

    private final int first;
    private final int second;
    private final int third;
    private final int resultCash;

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
