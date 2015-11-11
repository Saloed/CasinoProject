package frontend;


import authorizeClient.AuthorizeClient;
import authorizeClient.messages.MessageAuthorizeUser;
import authorizeClient.messages.MessageToAuthorizeClient;
import base.Abonent;
import base.Message;
import base.MessageSystem;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import messageSystem.AddressServiceImpl;
import messageSystem.MessageSystemImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SampleController
{

    public TextField login;
    public PasswordField pass;
    public MessageSystemImpl messageSystem;

    public void checkLogPass(ActionEvent actionEvent) {
        /* String lu = login.getText();
         String pu = pass.getText();

         Message msg = new MessageAuthorizeUser(messageSystem.getAddressService().getFrontEndAddress(),
         messageSystem.getAddressService().getAuthorizeClientAddress(),
         lu,pu,false);
         messageSystem.sendMessage(msg);
        */
        try{
            Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("ABC");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

}
