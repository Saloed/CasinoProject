package frontend.messages;

import base.Abonent;
import base.Address;
import base.Message;
import frontend.RouletteWindowController;

/**
 * Created by FedoR on 01.12.2015.
 */
public class MessageToRouletteWindowController extends Message {

    private int resultCash;
    private int result;

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
