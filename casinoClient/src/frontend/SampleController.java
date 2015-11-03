package frontend;


import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SampleController
{

    String l = "mamatvoya";
    String p = "ded228";
    public TextField login;
    public PasswordField pass;

    public void checkLogPass(ActionEvent actionEvent) {
         String lu = login.getText();
         String pu = pass.getText();
        if (lu.equals(l) && pu.equals(p))
        {
            System.out.println("good job");
        }
        else
        {
            System.out.println("Waning, not a valid infromation");
        }
    }

}
