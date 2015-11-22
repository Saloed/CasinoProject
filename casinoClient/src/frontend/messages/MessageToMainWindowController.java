package frontend.messages;

import base.Abonent;
import base.Address;
import base.Message;
import frontend.MainWindowController;

/**
 * Created by FedoR on 19.11.2015.
 */
public class MessageToMainWindowController extends Message{

    private String userName;
    private final String message;

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
