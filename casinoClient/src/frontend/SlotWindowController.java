package frontend;

import base.Abonent;
import base.Address;
import base.Message;
import base.MessageSystem;
import gameService.GameService;
import gameService.messages.MessageChangeCurrentGame;
import gameService.messages.MessageSendRequest;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import main.Main;

import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Created by FedoR on 26.11.2015.
 */
public class SlotWindowController implements Initializable, Abonent {


    public ImageView slot0;
    public ImageView slot1;
    public ImageView slot2;
    public Label currentCash;
    public TextField bet;
    public Label errorNoBet;
    public HBox horBox;
    public Button startButton;
    private Main application;
    private MessageSystem messageSystem;
    private final Address address = new Address();
    private final Random random = new Random();


    private final ExecutorService worker = Executors.newSingleThreadExecutor();

    public void setApp(Main application) {
        this.application = application;
        messageSystem = application.getMessageSystem();
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
        worker.execute(new WorkThread(messageSystem, this, "Slot"));
        GameService.GameType newGame = GameService.GameType.SLOT;
        Message msg = new MessageChangeCurrentGame(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), newGame);
        messageSystem.sendMessage(msg);
        currentCash.setText(Integer.toString(application.getCash()));

    }


    public void startRolling(ActionEvent event) {

        if ("".equals(bet.getText()))
            return;


        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), Integer.parseInt(bet.getText()), null);
        messageSystem.sendMessage(msg);
        //if (!bet.getText().matches("[A-Za-z]")) {

        //String.matches NE RABOTAET ?!

        //else
        //{
        //errorNoBet.setText("Invalid bet");
        //}
        startButton.disarm();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorNoBet.setText("");
    }

    public void keepRolling(int first, int second, int third, int resultCash) {
        Platform.runLater(new Task<Void>() {
            @Override
            protected Void call() throws Exception {


                Timeline firstSpin = new Timeline(
                        new KeyFrame(Duration.ZERO, new javafx.animation.KeyValue(slot0.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(250), new javafx.animation.KeyValue(slot0.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(500), new javafx.animation.KeyValue(slot0.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(750), new javafx.animation.KeyValue(slot0.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(1000), new javafx.animation.KeyValue(slot0.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(1250), new javafx.animation.KeyValue(slot0.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(1500), new javafx.animation.KeyValue(slot0.imageProperty(), new Image("/frontend/res/" + Integer.toString(first) + ".png")))
                );
                firstSpin.setCycleCount(4);
                firstSpin.play();


                Timeline secondSpin = new Timeline(
                        new KeyFrame(Duration.ZERO, new javafx.animation.KeyValue(slot1.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(250), new javafx.animation.KeyValue(slot1.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(500), new javafx.animation.KeyValue(slot1.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(750), new javafx.animation.KeyValue(slot1.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(1000), new javafx.animation.KeyValue(slot1.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(1250), new javafx.animation.KeyValue(slot1.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(1500), new javafx.animation.KeyValue(slot1.imageProperty(), new Image("/frontend/res/" + Integer.toString(second) + ".png")))
                );
                secondSpin.setCycleCount(4);
                secondSpin.play();

                Timeline thirdSpin = new Timeline(
                        new KeyFrame(Duration.ZERO, new javafx.animation.KeyValue(slot2.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(250), new javafx.animation.KeyValue(slot2.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(500), new javafx.animation.KeyValue(slot2.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(750), new javafx.animation.KeyValue(slot2.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(1000), new javafx.animation.KeyValue(slot2.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(1250), new javafx.animation.KeyValue(slot2.imageProperty(), new Image("/frontend/res/" + Integer.toString(random.nextInt(6) + 1) + ".png"))),
                        new KeyFrame(Duration.millis(1500), new javafx.animation.KeyValue(slot2.imageProperty(), new Image("/frontend/res/" + Integer.toString(third) + ".png")))
                );
                thirdSpin.setCycleCount(4);
                thirdSpin.play();


                currentCash.setText(Integer.toString(resultCash));
                startButton.arm();
                if (first == second && second == third) {
                    AudioClip johnCena = new AudioClip(new File("cena.mp3").toURI().toString());
                    johnCena.play();
                }
                return null;
            }


        });
    }


    @Override
    public Address getAddress() {
        return address;
    }

    public void goBack(ActionEvent event) {
        application.gotoMainWin();
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getSlotWindowControllerAddress()
                , messageSystem.getAddressService().getGameServiceAddress(), null, null);
        messageSystem.sendMessage(msg);
        stopController();
    }

    public void stopController() {
        worker.shutdown();
        try {
            worker.awaitTermination(15, TimeUnit.MILLISECONDS);
            worker.shutdownNow();
        } catch (InterruptedException e) {
            System.err.println("Error when shuttdown slot Controller");
        }
    }


}
