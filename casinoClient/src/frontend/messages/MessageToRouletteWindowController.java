package frontend.messages;

import base.Abonent;
import base.Address;
import base.Message;
import frontend.RouletteWindowController;

public class MessageToRouletteWindowController extends Message {

    private final int resultCash;
    private final int result;

    public MessageToRouletteWindowController(Address from, Address to, int resultCash, int result) {
        super(from, to);
        this.resultCash = resultCash;
        this.result = result;
    }

    @Override
    public void exec(Abonent abonent) {

        if (abonent instanceof RouletteWindowController) {
            RouletteWindowController get = (RouletteWindowController) abonent;
            get.setResult(resultCash, result);
        }
    }
}
