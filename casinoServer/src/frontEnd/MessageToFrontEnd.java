package frontEnd;

import base.Abonent;
import base.Address;
import base.Message;


public abstract class MessageToFrontEnd extends Message {

    public MessageToFrontEnd(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof FrontEnd) {
            exec((FrontEnd) abonent);
        }
    }

    protected abstract void exec(FrontEnd frontEnd);

}
