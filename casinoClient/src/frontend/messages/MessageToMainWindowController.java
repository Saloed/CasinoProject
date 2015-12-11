package frontend.messages;

import base.Abonent;
import base.Address;
import base.Message;
import frontend.MainWindowController;

public class MessageToMainWindowController extends Message{

    private final String message;
    private String userName;

    public MessageToMainWindowController(Address from, Address to,String message) {
        super(from, to);
        //this.userName = userName;
        this.message = message;
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof MainWindowController)
        {
            MainWindowController get = (MainWindowController) abonent;
            get.getMessage(message);
        }

    }
}
