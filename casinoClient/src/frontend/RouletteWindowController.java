package frontend;

import base.*;
import gameService.GameService;
import gameService.messages.MessageChangeCurrentGame;
import gameService.messages.MessageSendRequest;
import gameService.messages.MessageSendRouletteReuest;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import main.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RouletteWindowController implements Initializable, Abonent {
    private final Address address = new Address();
    private final ExecutorService worker = Executors.newSingleThreadExecutor();
    private final List<GameMessage.ServerRequest.Bet> betList = new ArrayList<>();
    private final List<Integer> betPlace = new ArrayList<>();
    public Button zer0;
    public Button r1;
    public Button b2;
    public Button r3;
    public Button b4;
    public Button r5;
    public Button b6;
    public Button r7;
    public Button b8;
    public Button r9;
    public Button b10;
    public Button b11;
    public Button r12;
    public Button b13;
    public Button r14;
    public Button b15;
    public Button r16;
    public Button b17;
    public Button r18;
    public Button r19;
    public Button b20;
    public Button r21;
    public Button b22;
    public Button r23;
    public Button b24;
    public Button r25;
    public Button b26;
    public Button r27;
    public Button b28;
    public Button b29;
    public Button r30;
    public Button b31;
    public Button r32;
    public Button b33;
    public Button r34;
    public Button b35;
    public Button r36;
    public Label cashField;
    private Main application;
    private MessageSystem messageSystem;
    //private int[] array = new int[36];


    public void setApp(Main application) {
        this.application = application;
        messageSystem = application.getMessageSystem();
        messageSystem.getAddressService().register(this);
        messageSystem.addService(this);
        worker.execute(new WorkThread(messageSystem, this, "Roulette"));

        GameService.GameType newGame = GameService.GameType.ROULETTE;
        Message msg = new MessageChangeCurrentGame(messageSystem.getAddressService().getSlotWindowControllerAddress(),
                messageSystem.getAddressService().getGameServiceAddress(), newGame);
        messageSystem.sendMessage(msg);
        cashField.setText("" + application.getCash());

    }

    public void startRoulette(ActionEvent event) {
        if (betList.isEmpty()) {
            return;
        }
        GameMessage.ServerRequest request = GameMessage.ServerRequest.newBuilder()
                .setGame(GameMessage.ServerRequest.GameType.ROULETTE)
                .addAllBet(betList)
                .setSessionId(0)
                .build();
        Message msg = new MessageSendRouletteReuest(address,
                messageSystem.getAddressService().getGameServiceAddress(),
                request);
        messageSystem.sendMessage(msg);
        betList.clear();
    }

    public void setResult(int resultCash, int result) {
        Platform.runLater(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                application.takeCash(resultCash);
                cashField.setText(Integer.toString(resultCash));
                System.out.println(result);

                return null;
            }
        });

    }

    public void goBack(ActionEvent event) {
        application.gotoMainWin();
        Message msg = new MessageSendRequest(messageSystem.getAddressService().getRouletteWindowControllerAddress()
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
            System.err.println("Error when shutdown roulette Controller");
        }
    }

    public void startDonateWindow(ActionEvent event) {

        BetWindowController bet = null;
        try {
            bet = (BetWindowController) application
                    .replaceSceneContent("/frontend/FXML/RouletteBetWindow.fxml", event, "BET!");

            //Button btn = (Button) event.getSource();
            //betPlace = btn.getId();
            bet.setApp(this, betPlace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acceptBet(GameMessage.ServerRequest.Bet bet) {
        betList.add(bet);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public void bet_r36(ActionEvent event) {
        betPlace.clear();
        betPlace.add(36);
        startDonateWindow(event);

    }

    public void bet_b35(ActionEvent event) {
        betPlace.clear();
        betPlace.add(35);
        startDonateWindow(event);

    }

    public void bet_r34(ActionEvent event) {
        betPlace.clear();
        betPlace.add(34);
        startDonateWindow(event);


    }

    public void bet_b33(ActionEvent event) {
        betPlace.clear();
        betPlace.add(33);
        startDonateWindow(event);

    }

    public void bet_r32(ActionEvent event) {
        betPlace.clear();
        betPlace.add(32);
        startDonateWindow(event);

    }

    public void bet_b31(ActionEvent event) {
        betPlace.clear();
        betPlace.add(31);
        startDonateWindow(event);

    }

    public void bet_r30(ActionEvent event) {
        betPlace.clear();
        betPlace.add(30);
        startDonateWindow(event);

    }

    public void bet_b29(ActionEvent event) {
        betPlace.clear();
        betPlace.add(29);
        startDonateWindow(event);


    }

    public void bet_b28(ActionEvent event) {
        betPlace.clear();
        betPlace.add(28);
        startDonateWindow(event);


    }

    public void bet_r27(ActionEvent event) {
        betPlace.clear();
        betPlace.add(27);
        startDonateWindow(event);

    }

    public void bet_b26(ActionEvent event) {
        betPlace.clear();
        betPlace.add(26);
        startDonateWindow(event);

    }

    public void bet_r25(ActionEvent event) {
        betPlace.clear();
        betPlace.add(25);
        startDonateWindow(event);


    }

    public void bet_b24(ActionEvent event) {
        betPlace.clear();
        betPlace.add(24);
        startDonateWindow(event);


    }

    public void bet_r23(ActionEvent event) {
        betPlace.clear();
        betPlace.add(23);
        startDonateWindow(event);


    }

    public void bet_b22(ActionEvent event) {
        betPlace.clear();
        betPlace.add(22);
        startDonateWindow(event);


    }

    public void bet_r21(ActionEvent event) {
        betPlace.clear();
        betPlace.add(21);
        startDonateWindow(event);


    }

    public void bet_b20(ActionEvent event) {
        betPlace.clear();
        betPlace.add(20);
        startDonateWindow(event);


    }

    public void bet_r19(ActionEvent event) {
        betPlace.clear();
        betPlace.add(19);
        startDonateWindow(event);


    }

    public void bet_r18(ActionEvent event) {
        betPlace.clear();
        betPlace.add(18);
        startDonateWindow(event);


    }

    public void bet_b17(ActionEvent event) {
        betPlace.clear();
        betPlace.add(17);
        startDonateWindow(event);

    }

    public void bet_r16(ActionEvent event) {
        betPlace.clear();
        betPlace.add(16);
        startDonateWindow(event);


    }

    public void bet_b15(ActionEvent event) {
        betPlace.clear();
        betPlace.add(15);
        startDonateWindow(event);


    }

    public void bet_r14(ActionEvent event) {
        betPlace.clear();
        betPlace.add(14);
        startDonateWindow(event);


    }

    public void bet_b13(ActionEvent event) {
        betPlace.clear();
        betPlace.add(13);
        startDonateWindow(event);


    }

    public void bet_r12(ActionEvent event) {
        betPlace.clear();
        betPlace.add(12);
        startDonateWindow(event);


    }

    public void bet_b11(ActionEvent event) {
        betPlace.clear();
        betPlace.add(11);
        startDonateWindow(event);


    }

    public void bet_b10(ActionEvent event) {
        betPlace.clear();
        betPlace.add(10);
        startDonateWindow(event);


    }

    public void bet_r9(ActionEvent event) {
        betPlace.clear();
        betPlace.add(9);
        startDonateWindow(event);

    }

    public void bet_b8(ActionEvent event) {
        betPlace.clear();
        betPlace.add(8);
        startDonateWindow(event);


    }

    public void bet_r7(ActionEvent event) {
        betPlace.clear();
        betPlace.add(7);
        startDonateWindow(event);


    }

    public void bet_b6(ActionEvent event) {
        betPlace.clear();
        betPlace.add(6);
        startDonateWindow(event);


    }

    public void bet_r5(ActionEvent event) {
        betPlace.clear();
        betPlace.add(5);
        startDonateWindow(event);

    }

    public void bet_b4(ActionEvent event) {
        betPlace.clear();
        betPlace.add(4);
        startDonateWindow(event);


    }

    public void bet_r3(ActionEvent event) {
        betPlace.clear();
        betPlace.add(3);
        startDonateWindow(event);


    }

    public void bet_b2(ActionEvent event) {
        betPlace.clear();
        betPlace.add(2);
        startDonateWindow(event);


    }

    public void bet_r1(ActionEvent event) {
        betPlace.clear();
        betPlace.add(1);
        startDonateWindow(event);


    }


    //lighting myass

    public void active3_6(Event event) {
        r3.setTextFill(Color.GOLD);
        b6.setTextFill(Color.GOLD);
    }

    public void disable3_6(Event event) {
        r3.setTextFill(Color.WHITE);
        b6.setTextFill(Color.WHITE);

    }

    public void active_1_18(Event event) {
        r1.setTextFill(Color.GOLD);
        b2.setTextFill(Color.GOLD);
        r3.setTextFill(Color.GOLD);
        b4.setTextFill(Color.GOLD);
        r5.setTextFill(Color.GOLD);
        b6.setTextFill(Color.GOLD);
        r7.setTextFill(Color.GOLD);
        b8.setTextFill(Color.GOLD);
        r9.setTextFill(Color.GOLD);
        b10.setTextFill(Color.GOLD);
        b11.setTextFill(Color.GOLD);
        r12.setTextFill(Color.GOLD);
        b13.setTextFill(Color.GOLD);
        r14.setTextFill(Color.GOLD);
        b15.setTextFill(Color.GOLD);
        r16.setTextFill(Color.GOLD);
        b17.setTextFill(Color.GOLD);
        r18.setTextFill(Color.GOLD);
    }

    public void disable_1_18(Event event) {
        r1.setTextFill(Color.WHITE);
        b2.setTextFill(Color.WHITE);
        r3.setTextFill(Color.WHITE);
        b4.setTextFill(Color.WHITE);
        r5.setTextFill(Color.WHITE);
        b6.setTextFill(Color.WHITE);
        r7.setTextFill(Color.WHITE);
        b8.setTextFill(Color.WHITE);
        r9.setTextFill(Color.WHITE);
        b10.setTextFill(Color.WHITE);
        b11.setTextFill(Color.WHITE);
        r12.setTextFill(Color.WHITE);
        b13.setTextFill(Color.WHITE);
        r14.setTextFill(Color.WHITE);
        b15.setTextFill(Color.WHITE);
        r16.setTextFill(Color.WHITE);
        b17.setTextFill(Color.WHITE);
        r18.setTextFill(Color.WHITE);

    }


    public void active_19_36(Event event) {
        r19.setTextFill(Color.GOLD);
        b20.setTextFill(Color.GOLD);
        r21.setTextFill(Color.GOLD);
        b22.setTextFill(Color.GOLD);
        r23.setTextFill(Color.GOLD);
        b24.setTextFill(Color.GOLD);
        r25.setTextFill(Color.GOLD);
        b26.setTextFill(Color.GOLD);
        r27.setTextFill(Color.GOLD);
        b28.setTextFill(Color.GOLD);
        b29.setTextFill(Color.GOLD);
        r30.setTextFill(Color.GOLD);
        b31.setTextFill(Color.GOLD);
        r32.setTextFill(Color.GOLD);
        b33.setTextFill(Color.GOLD);
        r34.setTextFill(Color.GOLD);
        b35.setTextFill(Color.GOLD);
        r36.setTextFill(Color.GOLD);

    }

    public void disable_19_36(Event event) {
        r19.setTextFill(Color.WHITE);
        b20.setTextFill(Color.WHITE);
        r21.setTextFill(Color.WHITE);
        b22.setTextFill(Color.WHITE);
        r23.setTextFill(Color.WHITE);
        b24.setTextFill(Color.WHITE);
        r25.setTextFill(Color.WHITE);
        b26.setTextFill(Color.WHITE);
        r27.setTextFill(Color.WHITE);
        b28.setTextFill(Color.WHITE);
        b29.setTextFill(Color.WHITE);
        r30.setTextFill(Color.WHITE);
        b31.setTextFill(Color.WHITE);
        r32.setTextFill(Color.WHITE);
        b33.setTextFill(Color.WHITE);
        r34.setTextFill(Color.WHITE);
        b35.setTextFill(Color.WHITE);
        r36.setTextFill(Color.WHITE);
    }

    public void active_31_32_33_34_35_36(Event event) {
        b31.setTextFill(Color.GOLD);
        r32.setTextFill(Color.GOLD);
        b33.setTextFill(Color.GOLD);
        r34.setTextFill(Color.GOLD);
        b35.setTextFill(Color.GOLD);
        r36.setTextFill(Color.GOLD);

    }

    public void disable_31_32_33_34_35_36(Event event) {
        b31.setTextFill(Color.WHITE);
        r32.setTextFill(Color.WHITE);
        b33.setTextFill(Color.WHITE);
        r34.setTextFill(Color.WHITE);
        b35.setTextFill(Color.WHITE);
        r36.setTextFill(Color.WHITE);
    }

    public void active_28_29_30_31_32_33(Event event) {
        b28.setTextFill(Color.GOLD);
        b29.setTextFill(Color.GOLD);
        r30.setTextFill(Color.GOLD);
        b31.setTextFill(Color.GOLD);
        r32.setTextFill(Color.GOLD);
        b33.setTextFill(Color.GOLD);
    }

    public void disable_28_29_30_31_32_33(Event event) {
        b28.setTextFill(Color.WHITE);
        b29.setTextFill(Color.WHITE);
        r30.setTextFill(Color.WHITE);
        b31.setTextFill(Color.WHITE);
        r32.setTextFill(Color.WHITE);
        b33.setTextFill(Color.WHITE);
    }

    public void active_25_26_27_28_29_30(Event event) {
        r25.setTextFill(Color.GOLD);
        b26.setTextFill(Color.GOLD);
        r27.setTextFill(Color.GOLD);
        b28.setTextFill(Color.GOLD);
        b29.setTextFill(Color.GOLD);
        r30.setTextFill(Color.GOLD);


    }

    public void disable_25_26_27_28_29_30(Event event) {
        r25.setTextFill(Color.WHITE);
        b26.setTextFill(Color.WHITE);
        r27.setTextFill(Color.WHITE);
        b28.setTextFill(Color.WHITE);
        b29.setTextFill(Color.WHITE);
        r30.setTextFill(Color.WHITE);

    }

    public void active_22_23_24_25_26_27(Event event) {
        b22.setTextFill(Color.GOLD);
        r23.setTextFill(Color.GOLD);
        b24.setTextFill(Color.GOLD);
        r25.setTextFill(Color.GOLD);
        b26.setTextFill(Color.GOLD);
        r27.setTextFill(Color.GOLD);

    }

    public void disable_22_23_24_25_26_27(Event event) {
        b22.setTextFill(Color.WHITE);
        r23.setTextFill(Color.WHITE);
        b24.setTextFill(Color.WHITE);
        r25.setTextFill(Color.WHITE);
        b26.setTextFill(Color.WHITE);
        r27.setTextFill(Color.WHITE);

    }

    public void active_19_20_21_22_23_24(Event event) {
        r19.setTextFill(Color.GOLD);
        b20.setTextFill(Color.GOLD);
        r21.setTextFill(Color.GOLD);
        b22.setTextFill(Color.GOLD);
        r23.setTextFill(Color.GOLD);
        b24.setTextFill(Color.GOLD);

    }

    public void disable_19_20_21_22_23_24(Event event) {
        r19.setTextFill(Color.WHITE);
        b20.setTextFill(Color.WHITE);
        r21.setTextFill(Color.WHITE);
        b22.setTextFill(Color.WHITE);
        r23.setTextFill(Color.WHITE);
        b24.setTextFill(Color.WHITE);

    }

    public void active_16_17_18_19_20_21(Event event) {
        r16.setTextFill(Color.GOLD);
        b17.setTextFill(Color.GOLD);
        r18.setTextFill(Color.GOLD);
        r19.setTextFill(Color.GOLD);
        b20.setTextFill(Color.GOLD);
        r21.setTextFill(Color.GOLD);

    }

    public void disable_16_17_18_19_20_21(Event event) {
        r16.setTextFill(Color.WHITE);
        b17.setTextFill(Color.WHITE);
        r18.setTextFill(Color.WHITE);
        r19.setTextFill(Color.WHITE);
        b20.setTextFill(Color.WHITE);
        r21.setTextFill(Color.WHITE);

    }

    public void active_13_14_15_16_17_18(Event event) {
        b13.setTextFill(Color.GOLD);
        r14.setTextFill(Color.GOLD);
        b15.setTextFill(Color.GOLD);
        r16.setTextFill(Color.GOLD);
        b17.setTextFill(Color.GOLD);
        r18.setTextFill(Color.GOLD);

    }

    public void disable_13_14_15_16_17_18(Event event) {
        b13.setTextFill(Color.WHITE);
        r14.setTextFill(Color.WHITE);
        b15.setTextFill(Color.WHITE);
        r16.setTextFill(Color.WHITE);
        b17.setTextFill(Color.WHITE);
        r18.setTextFill(Color.WHITE);

    }

    public void active_10_11_12_13_14_15(Event event) {
        b10.setTextFill(Color.GOLD);
        b11.setTextFill(Color.GOLD);
        r12.setTextFill(Color.GOLD);
        b13.setTextFill(Color.GOLD);
        r14.setTextFill(Color.GOLD);
        b15.setTextFill(Color.GOLD);

    }

    public void disable_10_11_12_13_14_15(Event event) {
        b10.setTextFill(Color.WHITE);
        b11.setTextFill(Color.WHITE);
        r12.setTextFill(Color.WHITE);
        b13.setTextFill(Color.WHITE);
        r14.setTextFill(Color.WHITE);
        b15.setTextFill(Color.WHITE);

    }

    public void active_7_8_9_10_11_12(Event event) {
        r7.setTextFill(Color.GOLD);
        b8.setTextFill(Color.GOLD);
        r9.setTextFill(Color.GOLD);
        b10.setTextFill(Color.GOLD);
        b11.setTextFill(Color.GOLD);
        r12.setTextFill(Color.GOLD);

    }

    public void disable_7_8_9_10_11_12(Event event) {
        r7.setTextFill(Color.GOLD);
        b8.setTextFill(Color.GOLD);
        r9.setTextFill(Color.GOLD);
        b10.setTextFill(Color.GOLD);
        b11.setTextFill(Color.GOLD);
        r12.setTextFill(Color.GOLD);

    }

    public void active_4_5_6_7_8_9(Event event) {
        b4.setTextFill(Color.GOLD);
        r5.setTextFill(Color.GOLD);
        b6.setTextFill(Color.GOLD);
        r7.setTextFill(Color.GOLD);
        b8.setTextFill(Color.GOLD);
        r9.setTextFill(Color.GOLD);

    }

    public void disable_4_5_6_7_8_9(Event event) {
        b4.setTextFill(Color.WHITE);
        r5.setTextFill(Color.WHITE);
        b6.setTextFill(Color.WHITE);
        r7.setTextFill(Color.WHITE);
        b8.setTextFill(Color.WHITE);
        r9.setTextFill(Color.WHITE);

    }

    public void active_0_2_1_3(Event event) {
        zer0.setTextFill(Color.GOLD);
        r1.setTextFill(Color.GOLD);
        b2.setTextFill(Color.GOLD);
        r3.setTextFill(Color.GOLD);

    }

    public void disable_0_2_1_3(Event event) {
        zer0.setTextFill(Color.WHITE);
        r1.setTextFill(Color.WHITE);
        b2.setTextFill(Color.WHITE);
        r3.setTextFill(Color.WHITE);

    }

    public void active_2_3_5_6(Event event) {
        b2.setTextFill(Color.GOLD);
        r3.setTextFill(Color.GOLD);
        r5.setTextFill(Color.GOLD);
        b6.setTextFill(Color.GOLD);

    }

    public void disable_2_3_5_6(Event event) {
        b2.setTextFill(Color.WHITE);
        r3.setTextFill(Color.WHITE);
        r5.setTextFill(Color.WHITE);
        b6.setTextFill(Color.WHITE);

    }

    public void active_1_2_4_5(Event event) {
        r1.setTextFill(Color.GOLD);
        b2.setTextFill(Color.GOLD);
        b4.setTextFill(Color.GOLD);
        r5.setTextFill(Color.GOLD);

    }

    public void disable_1_2_4_5(Event event) {
        r1.setTextFill(Color.WHITE);
        b2.setTextFill(Color.WHITE);
        b4.setTextFill(Color.WHITE);
        r5.setTextFill(Color.WHITE);

    }

    public void active_0_2_1(Event event) {
        zer0.setTextFill(Color.GOLD);
        b2.setTextFill(Color.GOLD);
        r1.setTextFill(Color.GOLD);

    }

    public void disable_0_2_1(Event event) {
        zer0.setTextFill(Color.WHITE);
        b2.setTextFill(Color.WHITE);
        r1.setTextFill(Color.WHITE);

    }

    public void active_0_2_3(Event event) {
        zer0.setTextFill(Color.GOLD);
        b2.setTextFill(Color.GOLD);
        r3.setTextFill(Color.GOLD);

    }

    public void disable_0_2_3(Event event) {
        zer0.setTextFill(Color.WHITE);
        b2.setTextFill(Color.WHITE);
        r3.setTextFill(Color.WHITE);

    }

    public void active_1_2_3_4_5_6(Event event) {
        r1.setTextFill(Color.GOLD);
        b2.setTextFill(Color.GOLD);
        r3.setTextFill(Color.GOLD);
        b4.setTextFill(Color.GOLD);
        r5.setTextFill(Color.GOLD);
        b6.setTextFill(Color.GOLD);


    }

    public void disable_1_2_3_4_5_6(Event event) {
        r1.setTextFill(Color.WHITE);
        b2.setTextFill(Color.WHITE);
        r3.setTextFill(Color.WHITE);
        b4.setTextFill(Color.WHITE);
        r5.setTextFill(Color.WHITE);
        b6.setTextFill(Color.WHITE);

    }

    public void active_even(Event event) {
        zer0.setTextFill(Color.GOLD);
        b2.setTextFill(Color.GOLD);
        b4.setTextFill(Color.GOLD);
        b6.setTextFill(Color.GOLD);
        b8.setTextFill(Color.GOLD);
        b10.setTextFill(Color.GOLD);
        r12.setTextFill(Color.GOLD);
        r14.setTextFill(Color.GOLD);
        r16.setTextFill(Color.GOLD);
        r18.setTextFill(Color.GOLD);
        b20.setTextFill(Color.GOLD);
        b22.setTextFill(Color.GOLD);
        b24.setTextFill(Color.GOLD);
        b26.setTextFill(Color.GOLD);
        b28.setTextFill(Color.GOLD);
        r30.setTextFill(Color.GOLD);
        r32.setTextFill(Color.GOLD);
        r34.setTextFill(Color.GOLD);
        r36.setTextFill(Color.GOLD);


    }

    public void disable_even(Event event) {
        zer0.setTextFill(Color.WHITE);
        b2.setTextFill(Color.WHITE);
        b4.setTextFill(Color.WHITE);
        b6.setTextFill(Color.WHITE);
        b8.setTextFill(Color.WHITE);
        b10.setTextFill(Color.WHITE);
        r12.setTextFill(Color.WHITE);
        r14.setTextFill(Color.WHITE);
        r16.setTextFill(Color.WHITE);
        r18.setTextFill(Color.WHITE);
        b20.setTextFill(Color.WHITE);
        b22.setTextFill(Color.WHITE);
        b24.setTextFill(Color.WHITE);
        b26.setTextFill(Color.WHITE);
        b28.setTextFill(Color.WHITE);
        r30.setTextFill(Color.WHITE);
        r32.setTextFill(Color.WHITE);
        r34.setTextFill(Color.WHITE);
        r36.setTextFill(Color.WHITE);

    }

    public void active_1_12(Event event) {
        r1.setTextFill(Color.GOLD);
        b2.setTextFill(Color.GOLD);
        r3.setTextFill(Color.GOLD);
        b4.setTextFill(Color.GOLD);
        r5.setTextFill(Color.GOLD);
        b6.setTextFill(Color.GOLD);
        r7.setTextFill(Color.GOLD);
        b8.setTextFill(Color.GOLD);
        r9.setTextFill(Color.GOLD);
        b10.setTextFill(Color.GOLD);
        b11.setTextFill(Color.GOLD);
        r12.setTextFill(Color.GOLD);
    }

    public void disable_1_12(Event event) {
        r1.setTextFill(Color.WHITE);
        b2.setTextFill(Color.WHITE);
        r3.setTextFill(Color.WHITE);
        b4.setTextFill(Color.WHITE);
        r5.setTextFill(Color.WHITE);
        b6.setTextFill(Color.WHITE);
        r7.setTextFill(Color.WHITE);
        b8.setTextFill(Color.WHITE);
        r9.setTextFill(Color.WHITE);
        b10.setTextFill(Color.WHITE);
        b11.setTextFill(Color.WHITE);
        r12.setTextFill(Color.WHITE);

    }

    public void active_12_24(Event event) {
        b13.setTextFill(Color.GOLD);
        r14.setTextFill(Color.GOLD);
        b15.setTextFill(Color.GOLD);
        r16.setTextFill(Color.GOLD);
        b17.setTextFill(Color.GOLD);
        r18.setTextFill(Color.GOLD);
        r19.setTextFill(Color.GOLD);
        b20.setTextFill(Color.GOLD);
        r21.setTextFill(Color.GOLD);
        b22.setTextFill(Color.GOLD);
        r23.setTextFill(Color.GOLD);
        b24.setTextFill(Color.GOLD);

    }

    public void disable_12_24(Event event) {
        b13.setTextFill(Color.WHITE);
        r14.setTextFill(Color.WHITE);
        b15.setTextFill(Color.WHITE);
        r16.setTextFill(Color.WHITE);
        b17.setTextFill(Color.WHITE);
        r18.setTextFill(Color.WHITE);
        r19.setTextFill(Color.WHITE);
        b20.setTextFill(Color.WHITE);
        r21.setTextFill(Color.WHITE);
        b22.setTextFill(Color.WHITE);
        r23.setTextFill(Color.WHITE);
        b24.setTextFill(Color.WHITE);

    }

    public void active_24_36(Event event) {
        r25.setTextFill(Color.GOLD);
        b26.setTextFill(Color.GOLD);
        r27.setTextFill(Color.GOLD);
        b28.setTextFill(Color.GOLD);
        b29.setTextFill(Color.GOLD);
        r30.setTextFill(Color.GOLD);
        b31.setTextFill(Color.GOLD);
        r32.setTextFill(Color.GOLD);
        b33.setTextFill(Color.GOLD);
        r34.setTextFill(Color.GOLD);
        b35.setTextFill(Color.GOLD);
        r36.setTextFill(Color.GOLD);

    }

    public void disable_24_36(Event event) {
        r25.setTextFill(Color.WHITE);
        b26.setTextFill(Color.WHITE);
        r27.setTextFill(Color.WHITE);
        b28.setTextFill(Color.WHITE);
        b29.setTextFill(Color.WHITE);
        r30.setTextFill(Color.WHITE);
        b31.setTextFill(Color.WHITE);
        r32.setTextFill(Color.WHITE);
        b33.setTextFill(Color.WHITE);
        r34.setTextFill(Color.WHITE);
        b35.setTextFill(Color.WHITE);
        r36.setTextFill(Color.WHITE);

    }

    public void active_r(Event event) {
        r1.setTextFill(Color.GOLD);
        r3.setTextFill(Color.GOLD);
        r5.setTextFill(Color.GOLD);
        r7.setTextFill(Color.GOLD);
        r9.setTextFill(Color.GOLD);
        r12.setTextFill(Color.GOLD);
        r14.setTextFill(Color.GOLD);
        r16.setTextFill(Color.GOLD);
        r18.setTextFill(Color.GOLD);
        r19.setTextFill(Color.GOLD);
        r21.setTextFill(Color.GOLD);
        r23.setTextFill(Color.GOLD);
        r25.setTextFill(Color.GOLD);
        r27.setTextFill(Color.GOLD);
        r30.setTextFill(Color.GOLD);
        r32.setTextFill(Color.GOLD);
        r34.setTextFill(Color.GOLD);
        r36.setTextFill(Color.GOLD);

    }

    public void disable_r(Event event) {
        r1.setTextFill(Color.WHITE);
        r3.setTextFill(Color.WHITE);
        r5.setTextFill(Color.WHITE);
        r7.setTextFill(Color.WHITE);
        r9.setTextFill(Color.WHITE);
        r12.setTextFill(Color.WHITE);
        r14.setTextFill(Color.WHITE);
        r16.setTextFill(Color.WHITE);
        r18.setTextFill(Color.WHITE);
        r19.setTextFill(Color.WHITE);
        r21.setTextFill(Color.WHITE);
        r23.setTextFill(Color.WHITE);
        r25.setTextFill(Color.WHITE);
        r27.setTextFill(Color.WHITE);
        r30.setTextFill(Color.WHITE);
        r32.setTextFill(Color.WHITE);
        r34.setTextFill(Color.WHITE);
        r36.setTextFill(Color.WHITE);

    }

    public void active_b(Event event) {
        b2.setTextFill(Color.GOLD);
        b4.setTextFill(Color.GOLD);
        b6.setTextFill(Color.GOLD);
        b8.setTextFill(Color.GOLD);
        b10.setTextFill(Color.GOLD);
        b11.setTextFill(Color.GOLD);
        b13.setTextFill(Color.GOLD);
        b15.setTextFill(Color.GOLD);
        b17.setTextFill(Color.GOLD);
        b20.setTextFill(Color.GOLD);
        b22.setTextFill(Color.GOLD);
        b24.setTextFill(Color.GOLD);
        b26.setTextFill(Color.GOLD);
        b28.setTextFill(Color.GOLD);
        b29.setTextFill(Color.GOLD);
        b31.setTextFill(Color.GOLD);
        b33.setTextFill(Color.GOLD);
        b35.setTextFill(Color.GOLD);

    }

    public void disable_b(Event event) {
        b2.setTextFill(Color.WHITE);
        b4.setTextFill(Color.WHITE);
        b6.setTextFill(Color.WHITE);
        b8.setTextFill(Color.WHITE);
        b10.setTextFill(Color.WHITE);
        b11.setTextFill(Color.WHITE);
        b13.setTextFill(Color.WHITE);
        b15.setTextFill(Color.WHITE);
        b17.setTextFill(Color.WHITE);
        b20.setTextFill(Color.WHITE);
        b22.setTextFill(Color.WHITE);
        b24.setTextFill(Color.WHITE);
        b26.setTextFill(Color.WHITE);
        b28.setTextFill(Color.WHITE);
        b29.setTextFill(Color.WHITE);
        b31.setTextFill(Color.WHITE);
        b33.setTextFill(Color.WHITE);
        b35.setTextFill(Color.WHITE);

    }

    public void active_odd(Event event) {
        r1.setTextFill(Color.GOLD);
        r3.setTextFill(Color.GOLD);
        r5.setTextFill(Color.GOLD);
        r7.setTextFill(Color.GOLD);
        r9.setTextFill(Color.GOLD);
        b11.setTextFill(Color.GOLD);
        b13.setTextFill(Color.GOLD);
        b15.setTextFill(Color.GOLD);
        b17.setTextFill(Color.GOLD);
        r19.setTextFill(Color.GOLD);
        r21.setTextFill(Color.GOLD);
        r23.setTextFill(Color.GOLD);
        r25.setTextFill(Color.GOLD);
        r27.setTextFill(Color.GOLD);
        b29.setTextFill(Color.GOLD);
        b31.setTextFill(Color.GOLD);
        b33.setTextFill(Color.GOLD);
        b35.setTextFill(Color.GOLD);

    }

    public void disable_odd(Event event) {
        r1.setTextFill(Color.WHITE);
        r3.setTextFill(Color.WHITE);
        r5.setTextFill(Color.WHITE);
        r7.setTextFill(Color.WHITE);
        r9.setTextFill(Color.WHITE);
        b11.setTextFill(Color.WHITE);
        b13.setTextFill(Color.WHITE);
        b15.setTextFill(Color.WHITE);
        b17.setTextFill(Color.WHITE);
        r19.setTextFill(Color.WHITE);
        r21.setTextFill(Color.WHITE);
        r23.setTextFill(Color.WHITE);
        r25.setTextFill(Color.WHITE);
        r27.setTextFill(Color.WHITE);
        b29.setTextFill(Color.WHITE);
        b31.setTextFill(Color.WHITE);
        b33.setTextFill(Color.WHITE);
        b35.setTextFill(Color.WHITE);

    }

    public void active_top(Event event) {
        r3.setTextFill(Color.GOLD);
        b6.setTextFill(Color.GOLD);
        r9.setTextFill(Color.GOLD);
        r12.setTextFill(Color.GOLD);
        b15.setTextFill(Color.GOLD);
        r18.setTextFill(Color.GOLD);
        r21.setTextFill(Color.GOLD);
        b24.setTextFill(Color.GOLD);
        r27.setTextFill(Color.GOLD);
        r30.setTextFill(Color.GOLD);
        b33.setTextFill(Color.GOLD);
        r36.setTextFill(Color.GOLD);

    }

    public void disable_top(Event event) {
        r3.setTextFill(Color.WHITE);
        b6.setTextFill(Color.WHITE);
        r9.setTextFill(Color.WHITE);
        r12.setTextFill(Color.WHITE);
        b15.setTextFill(Color.WHITE);
        r18.setTextFill(Color.WHITE);
        r21.setTextFill(Color.WHITE);
        b24.setTextFill(Color.WHITE);
        r27.setTextFill(Color.WHITE);
        r30.setTextFill(Color.WHITE);
        b33.setTextFill(Color.WHITE);
        r36.setTextFill(Color.WHITE);

    }

    public void active_mid(Event event) {
        b2.setTextFill(Color.GOLD);
        r5.setTextFill(Color.GOLD);
        b8.setTextFill(Color.GOLD);
        b11.setTextFill(Color.GOLD);
        r14.setTextFill(Color.GOLD);
        b17.setTextFill(Color.GOLD);
        b20.setTextFill(Color.GOLD);
        r23.setTextFill(Color.GOLD);
        b26.setTextFill(Color.GOLD);
        b29.setTextFill(Color.GOLD);
        r32.setTextFill(Color.GOLD);
        b35.setTextFill(Color.GOLD);

    }

    public void disable_mid(Event event) {
        b2.setTextFill(Color.WHITE);
        r5.setTextFill(Color.WHITE);
        b8.setTextFill(Color.WHITE);
        b11.setTextFill(Color.WHITE);
        r14.setTextFill(Color.WHITE);
        b17.setTextFill(Color.WHITE);
        b20.setTextFill(Color.WHITE);
        r23.setTextFill(Color.WHITE);
        b26.setTextFill(Color.WHITE);
        b29.setTextFill(Color.WHITE);
        r32.setTextFill(Color.WHITE);
        b35.setTextFill(Color.WHITE);

    }

    public void active_bot(Event event) {
        r1.setTextFill(Color.GOLD);
        b4.setTextFill(Color.GOLD);
        r7.setTextFill(Color.GOLD);
        b10.setTextFill(Color.GOLD);
        b13.setTextFill(Color.GOLD);
        r16.setTextFill(Color.GOLD);
        r19.setTextFill(Color.GOLD);
        b22.setTextFill(Color.GOLD);
        r25.setTextFill(Color.GOLD);
        b28.setTextFill(Color.GOLD);
        b31.setTextFill(Color.GOLD);
        r34.setTextFill(Color.GOLD);


    }

    public void disable_bot(Event event) {
        r1.setTextFill(Color.WHITE);
        b4.setTextFill(Color.WHITE);
        r7.setTextFill(Color.WHITE);
        b10.setTextFill(Color.WHITE);
        b13.setTextFill(Color.WHITE);
        r16.setTextFill(Color.WHITE);
        r19.setTextFill(Color.WHITE);
        b22.setTextFill(Color.WHITE);
        r25.setTextFill(Color.WHITE);
        b28.setTextFill(Color.WHITE);
        b31.setTextFill(Color.WHITE);
        r34.setTextFill(Color.WHITE);

    }

    public void actvie_35_36(Event event) {
        b35.setTextFill(Color.GOLD);
        r36.setTextFill(Color.GOLD);

    }

    public void disable_35_36(Event event) {
        b35.setTextFill(Color.WHITE);
        r36.setTextFill(Color.WHITE);

    }

    public void actvie_34_35(Event event) {
        r34.setTextFill(Color.GOLD);
        b35.setTextFill(Color.GOLD);

    }

    public void disable_34_35(Event event) {
        r34.setTextFill(Color.WHITE);
        b35.setTextFill(Color.WHITE);

    }

    public void active_34_35_36(Event event) {
        r34.setTextFill(Color.GOLD);
        b35.setTextFill(Color.GOLD);
        r36.setTextFill(Color.GOLD);

    }

    public void disbale_34_35_36(Event event) {
        r34.setTextFill(Color.WHITE);
        b35.setTextFill(Color.WHITE);
        r36.setTextFill(Color.WHITE);

    }

    public void actvie_32_33(Event event) {
        r32.setTextFill(Color.GOLD);
        b33.setTextFill(Color.GOLD);

    }

    public void disable_32_33(Event event) {
        r32.setTextFill(Color.WHITE);
        b33.setTextFill(Color.WHITE);

    }

    public void actvie_31_32(Event event) {
        b31.setTextFill(Color.GOLD);
        r32.setTextFill(Color.GOLD);

    }

    public void disable_31_32(Event event) {
        b31.setTextFill(Color.WHITE);
        r32.setTextFill(Color.WHITE);

    }

    public void actvie_31_34(Event event) {
        b31.setTextFill(Color.GOLD);
        r34.setTextFill(Color.GOLD);

    }

    public void disable_31_34(Event event) {
        b31.setTextFill(Color.WHITE);
        r34.setTextFill(Color.WHITE);

    }

    public void actvie_32_35(Event event) {
        r32.setTextFill(Color.GOLD);
        b35.setTextFill(Color.GOLD);

    }

    public void disable_32_35(Event event) {
        r32.setTextFill(Color.WHITE);
        b35.setTextFill(Color.WHITE);

    }

    public void actvie_33_36(Event event) {
        b33.setTextFill(Color.GOLD);
        r36.setTextFill(Color.GOLD);

    }

    public void disable_33_36(Event event) {
        b33.setTextFill(Color.WHITE);
        r36.setTextFill(Color.WHITE);

    }

    public void active_32_33_35_36(Event event) {
        r32.setTextFill(Color.GOLD);
        b33.setTextFill(Color.GOLD);
        b35.setTextFill(Color.GOLD);
        r36.setTextFill(Color.GOLD);


    }

    public void disable_32_33_35_36(Event event) {
        r32.setTextFill(Color.WHITE);
        b33.setTextFill(Color.WHITE);
        b35.setTextFill(Color.WHITE);
        r36.setTextFill(Color.WHITE);

    }

    public void active_31_32_34_35(Event event) {
        b31.setTextFill(Color.GOLD);
        r32.setTextFill(Color.GOLD);
        r34.setTextFill(Color.GOLD);
        b35.setTextFill(Color.GOLD);

    }

    public void disable_31_32_34_35(Event event) {
        b31.setTextFill(Color.WHITE);
        r32.setTextFill(Color.WHITE);
        r34.setTextFill(Color.WHITE);
        b35.setTextFill(Color.WHITE);

    }

    public void active_31_32_33(Event event) {
        b31.setTextFill(Color.GOLD);
        r32.setTextFill(Color.GOLD);
        b33.setTextFill(Color.GOLD);

    }

    public void disable_31_32_33(Event event) {
        b31.setTextFill(Color.WHITE);
        r32.setTextFill(Color.WHITE);
        b33.setTextFill(Color.WHITE);

    }

    public void actvie_29_30(Event event) {
        b29.setTextFill(Color.GOLD);
        r30.setTextFill(Color.GOLD);

    }

    public void disable_29_30(Event event) {
        b29.setTextFill(Color.WHITE);
        r30.setTextFill(Color.WHITE);

    }

    public void actvie_28_29(Event event) {
        b28.setTextFill(Color.GOLD);
        b29.setTextFill(Color.GOLD);

    }

    public void disable_28_29(Event event) {
        b28.setTextFill(Color.WHITE);
        b29.setTextFill(Color.WHITE);

    }

    public void actvie_28_31(Event event) {
        b28.setTextFill(Color.GOLD);
        b31.setTextFill(Color.GOLD);

    }

    public void disable_28_31(Event event) {
        b28.setTextFill(Color.WHITE);
        b31.setTextFill(Color.WHITE);

    }

    public void actvie_29_32(Event event) {
        b29.setTextFill(Color.GOLD);
        r32.setTextFill(Color.GOLD);


    }

    public void disable_29_32(Event event) {
        b29.setTextFill(Color.WHITE);
        r32.setTextFill(Color.WHITE);

    }

    public void actvie_30_33(Event event) {
        r30.setTextFill(Color.GOLD);
        b33.setTextFill(Color.GOLD);

    }

    public void disable_30_33(Event event) {
        r30.setTextFill(Color.WHITE);
        b33.setTextFill(Color.WHITE);

    }

    public void active_29_30_32_33(Event event) {
        b29.setTextFill(Color.GOLD);
        r30.setTextFill(Color.GOLD);
        r32.setTextFill(Color.GOLD);
        b33.setTextFill(Color.GOLD);

    }

    public void disable_29_30_32_33(Event event) {
        b29.setTextFill(Color.WHITE);
        r30.setTextFill(Color.WHITE);
        r32.setTextFill(Color.WHITE);
        b33.setTextFill(Color.WHITE);

    }

    public void active_28_29_31_32(Event event) {
        b28.setTextFill(Color.GOLD);
        b29.setTextFill(Color.GOLD);
        b31.setTextFill(Color.GOLD);
        r32.setTextFill(Color.GOLD);

    }

    public void disable_28_29_31_32(Event event) {
        b28.setTextFill(Color.WHITE);
        b29.setTextFill(Color.WHITE);
        b31.setTextFill(Color.WHITE);
        r32.setTextFill(Color.WHITE);

    }

    public void active_28_29_30(Event event) {
        b28.setTextFill(Color.GOLD);
        b29.setTextFill(Color.GOLD);
        r30.setTextFill(Color.GOLD);

    }

    public void disable_28_29_30(Event event) {
        b28.setTextFill(Color.WHITE);
        b29.setTextFill(Color.WHITE);
        r30.setTextFill(Color.WHITE);

    }

    public void actvie_26_27(Event event) {
        b26.setTextFill(Color.GOLD);
        r27.setTextFill(Color.GOLD);

    }

    public void disable_26_27(Event event) {
        b26.setTextFill(Color.WHITE);
        r27.setTextFill(Color.WHITE);

    }

    public void actvie_25_26(Event event) {
        r25.setTextFill(Color.GOLD);
        b26.setTextFill(Color.GOLD);

    }

    public void disable_25_26(Event event) {
        r25.setTextFill(Color.WHITE);
        b26.setTextFill(Color.WHITE);

    }

    public void actvie_25_28(Event event) {
        r25.setTextFill(Color.GOLD);
        b28.setTextFill(Color.GOLD);

    }

    public void disable_25_28(Event event) {
        r25.setTextFill(Color.WHITE);
        b28.setTextFill(Color.WHITE);

    }

    public void actvie_26_29(Event event) {
        b26.setTextFill(Color.GOLD);
        b29.setTextFill(Color.GOLD);

    }

    public void disable_26_29(Event event) {
        b26.setTextFill(Color.WHITE);
        b29.setTextFill(Color.WHITE);

    }

    public void actvie_27_30(Event event) {
        r27.setTextFill(Color.GOLD);
        r30.setTextFill(Color.GOLD);

    }

    public void disable_27_30(Event event) {
        r27.setTextFill(Color.WHITE);
        r30.setTextFill(Color.WHITE);

    }

    public void active_26_27_29_30(Event event) {
        b26.setTextFill(Color.GOLD);
        r27.setTextFill(Color.GOLD);
        b29.setTextFill(Color.GOLD);
        r30.setTextFill(Color.GOLD);

    }

    public void disable_26_27_29_30(Event event) {
        b26.setTextFill(Color.WHITE);
        r27.setTextFill(Color.WHITE);
        b29.setTextFill(Color.WHITE);
        r30.setTextFill(Color.WHITE);

    }

    public void active_25_26_28_29(Event event) {
        r25.setTextFill(Color.GOLD);
        b26.setTextFill(Color.GOLD);
        b28.setTextFill(Color.GOLD);
        b29.setTextFill(Color.GOLD);

    }

    public void disable_25_26_28_29(Event event) {
        r25.setTextFill(Color.WHITE);
        b26.setTextFill(Color.WHITE);
        b28.setTextFill(Color.WHITE);
        b29.setTextFill(Color.WHITE);

    }

    public void active_25_26_27(Event event) {
        r25.setTextFill(Color.GOLD);
        b26.setTextFill(Color.GOLD);
        r27.setTextFill(Color.GOLD);

    }

    public void disable_25_26_27(Event event) {
        r25.setTextFill(Color.WHITE);
        b26.setTextFill(Color.WHITE);
        r27.setTextFill(Color.WHITE);

    }

    public void actvie_23_24(Event event) {
        r23.setTextFill(Color.GOLD);
        b24.setTextFill(Color.GOLD);

    }

    public void disable_23_24(Event event) {
        r23.setTextFill(Color.WHITE);
        b24.setTextFill(Color.WHITE);

    }

    public void actvie_22_23(Event event) {
        b22.setTextFill(Color.GOLD);
        r23.setTextFill(Color.GOLD);

    }

    public void disable_22_23(Event event) {
        b22.setTextFill(Color.WHITE);
        r23.setTextFill(Color.WHITE);

    }

    public void actvie_22_25(Event event) {
        b22.setTextFill(Color.GOLD);
        r25.setTextFill(Color.GOLD);

    }

    public void disable_22_25(Event event) {
        b22.setTextFill(Color.WHITE);
        r25.setTextFill(Color.WHITE);

    }

    public void actvie_23_26(Event event) {
        r23.setTextFill(Color.GOLD);
        b26.setTextFill(Color.GOLD);

    }

    public void disable_23_26(Event event) {
        r23.setTextFill(Color.WHITE);
        b26.setTextFill(Color.WHITE);

    }

    public void actvie_24_27(Event event) {
        b24.setTextFill(Color.GOLD);
        r27.setTextFill(Color.GOLD);

    }

    public void disable_24_27(Event event) {
        b24.setTextFill(Color.WHITE);
        r27.setTextFill(Color.WHITE);

    }

    public void active_23_24_26_27(Event event) {
        r23.setTextFill(Color.GOLD);
        b24.setTextFill(Color.GOLD);
        b26.setTextFill(Color.GOLD);
        r27.setTextFill(Color.GOLD);

    }

    public void disable_23_24_26_27(Event event) {
        r23.setTextFill(Color.WHITE);
        b24.setTextFill(Color.WHITE);
        b26.setTextFill(Color.WHITE);
        r27.setTextFill(Color.WHITE);

    }

    public void active_22_23_25_26(Event event) {
        b22.setTextFill(Color.GOLD);
        r23.setTextFill(Color.GOLD);
        r25.setTextFill(Color.GOLD);
        b26.setTextFill(Color.GOLD);

    }

    public void disable_22_23_25_26(Event event) {
        b22.setTextFill(Color.WHITE);
        r23.setTextFill(Color.WHITE);
        r25.setTextFill(Color.WHITE);
        b26.setTextFill(Color.WHITE);


    }

    public void active_22_23_24(Event event) {
        b22.setTextFill(Color.GOLD);
        r23.setTextFill(Color.GOLD);
        b24.setTextFill(Color.GOLD);

    }

    public void disable_22_23_24(Event event) {
        b22.setTextFill(Color.WHITE);
        r23.setTextFill(Color.WHITE);
        b24.setTextFill(Color.WHITE);


    }

    public void actvie_20_21(Event event) {
        b20.setTextFill(Color.GOLD);
        r21.setTextFill(Color.GOLD);

    }

    public void disable_20_21(Event event) {
        b20.setTextFill(Color.WHITE);
        r21.setTextFill(Color.WHITE);

    }

    public void actvie_19_20(Event event) {
        r19.setTextFill(Color.GOLD);
        b20.setTextFill(Color.GOLD);

    }

    public void disable_19_20(Event event) {
        r19.setTextFill(Color.WHITE);
        b20.setTextFill(Color.WHITE);

    }

    public void actvie_19_22(Event event) {
        r19.setTextFill(Color.GOLD);
        b22.setTextFill(Color.GOLD);

    }

    public void disable_19_22(Event event) {
        r19.setTextFill(Color.WHITE);
        b22.setTextFill(Color.WHITE);

    }

    public void actvie_20_23(Event event) {
        b20.setTextFill(Color.GOLD);
        r23.setTextFill(Color.GOLD);

    }

    public void disable_20_23(Event event) {
        b20.setTextFill(Color.WHITE);
        r23.setTextFill(Color.WHITE);

    }

    public void actvie_21_24(Event event) {
        r21.setTextFill(Color.GOLD);
        b24.setTextFill(Color.GOLD);

    }

    public void disable_21_24(Event event) {
        r21.setTextFill(Color.WHITE);
        b24.setTextFill(Color.WHITE);

    }

    public void active_20_21_23_24(Event event) {
        b20.setTextFill(Color.GOLD);
        r21.setTextFill(Color.GOLD);
        r23.setTextFill(Color.GOLD);
        b24.setTextFill(Color.GOLD);

    }

    public void disable_20_21_23_24(Event event) {
        b20.setTextFill(Color.WHITE);
        r21.setTextFill(Color.WHITE);
        r23.setTextFill(Color.WHITE);
        b24.setTextFill(Color.WHITE);

    }

    public void active_19_20_22_23(Event event) {
        r19.setTextFill(Color.GOLD);
        b20.setTextFill(Color.GOLD);
        b22.setTextFill(Color.GOLD);
        r23.setTextFill(Color.GOLD);

    }

    public void disable_19_20_22_23(Event event) {
        r19.setTextFill(Color.WHITE);
        b20.setTextFill(Color.WHITE);
        b22.setTextFill(Color.WHITE);
        r23.setTextFill(Color.WHITE);

    }

    public void active_19_20_21(Event event) {
        r19.setTextFill(Color.GOLD);
        b20.setTextFill(Color.GOLD);
        r21.setTextFill(Color.GOLD);

    }

    public void disable_19_20_21(Event event) {
        r19.setTextFill(Color.WHITE);
        b20.setTextFill(Color.WHITE);
        r21.setTextFill(Color.WHITE);

    }

    public void actvie_17_18(Event event) {
        b17.setTextFill(Color.GOLD);
        r18.setTextFill(Color.GOLD);

    }

    public void disable_17_18(Event event) {
        b17.setTextFill(Color.WHITE);
        r18.setTextFill(Color.WHITE);

    }

    public void actvie_16_17(Event event) {
        r16.setTextFill(Color.GOLD);
        b17.setTextFill(Color.GOLD);

    }

    public void disable_16_17(Event event) {
        r16.setTextFill(Color.WHITE);
        b17.setTextFill(Color.WHITE);

    }

    public void actvie_16_19(Event event) {
        r16.setTextFill(Color.GOLD);
        r19.setTextFill(Color.GOLD);

    }

    public void disable_16_19(Event event) {
        r16.setTextFill(Color.WHITE);
        r19.setTextFill(Color.WHITE);

    }

    public void actvie_17_20(Event event) {
        b17.setTextFill(Color.GOLD);
        b20.setTextFill(Color.GOLD);

    }

    public void disable_17_20(Event event) {
        b17.setTextFill(Color.WHITE);
        b20.setTextFill(Color.WHITE);

    }

    public void actvie_18_21(Event event) {
        r18.setTextFill(Color.GOLD);
        r21.setTextFill(Color.GOLD);

    }

    public void disable_18_21(Event event) {
        r18.setTextFill(Color.WHITE);
        r21.setTextFill(Color.WHITE);

    }

    public void active_17_18_20_21(Event event) {
        b17.setTextFill(Color.GOLD);
        r18.setTextFill(Color.GOLD);
        b20.setTextFill(Color.GOLD);
        r21.setTextFill(Color.GOLD);

    }

    public void disable_17_18_20_21(Event event) {
        b17.setTextFill(Color.WHITE);
        r18.setTextFill(Color.WHITE);
        b20.setTextFill(Color.WHITE);
        r21.setTextFill(Color.WHITE);

    }

    public void active_16_17_19_20(Event event) {
        r16.setTextFill(Color.GOLD);
        b17.setTextFill(Color.GOLD);
        r19.setTextFill(Color.GOLD);
        b20.setTextFill(Color.GOLD);

    }

    public void disable_16_17_19_20(Event event) {
        r16.setTextFill(Color.WHITE);
        b17.setTextFill(Color.WHITE);
        r19.setTextFill(Color.WHITE);
        b20.setTextFill(Color.WHITE);

    }

    public void active_16_17_18(Event event) {
        r16.setTextFill(Color.GOLD);
        b17.setTextFill(Color.GOLD);
        r18.setTextFill(Color.GOLD);

    }

    public void disable_16_17_18(Event event) {
        r16.setTextFill(Color.WHITE);
        b17.setTextFill(Color.WHITE);
        r18.setTextFill(Color.WHITE);

    }

    public void actvie_14_15(Event event) {
        r14.setTextFill(Color.GOLD);
        b15.setTextFill(Color.GOLD);

    }

    public void disable_14_15(Event event) {
        r14.setTextFill(Color.WHITE);
        b15.setTextFill(Color.WHITE);

    }

    public void actvie_13_14(Event event) {
        b13.setTextFill(Color.GOLD);
        r14.setTextFill(Color.GOLD);

    }

    public void disable_13_14(Event event) {
        b13.setTextFill(Color.WHITE);
        r14.setTextFill(Color.WHITE);

    }

    public void actvie_13_16(Event event) {
        b13.setTextFill(Color.GOLD);
        r16.setTextFill(Color.GOLD);

    }

    public void disable_13_16(Event event) {
        b13.setTextFill(Color.WHITE);
        r16.setTextFill(Color.WHITE);

    }

    public void actvie_14_17(Event event) {
        r14.setTextFill(Color.GOLD);
        b17.setTextFill(Color.GOLD);

    }

    public void disable_14_17(Event event) {
        r14.setTextFill(Color.WHITE);
        b17.setTextFill(Color.WHITE);

    }

    public void actvie_15_18(Event event) {
        b15.setTextFill(Color.GOLD);
        r18.setTextFill(Color.GOLD);

    }

    public void disable_15_18(Event event) {
        b15.setTextFill(Color.WHITE);
        r18.setTextFill(Color.WHITE);

    }

    public void active_14_15_17_18(Event event) {
        r14.setTextFill(Color.GOLD);
        b15.setTextFill(Color.GOLD);
        b17.setTextFill(Color.GOLD);
        r18.setTextFill(Color.GOLD);

    }

    public void disable_14_15_17_18(Event event) {
        r14.setTextFill(Color.WHITE);
        b15.setTextFill(Color.WHITE);
        b17.setTextFill(Color.WHITE);
        r18.setTextFill(Color.WHITE);

    }

    public void active_13_14_16_17(Event event) {
        b13.setTextFill(Color.GOLD);
        r14.setTextFill(Color.GOLD);
        r16.setTextFill(Color.GOLD);
        b17.setTextFill(Color.GOLD);

    }

    public void disable_13_14_16_17(Event event) {
        b13.setTextFill(Color.WHITE);
        r14.setTextFill(Color.WHITE);
        r16.setTextFill(Color.WHITE);
        b17.setTextFill(Color.WHITE);

    }

    public void active_13_14_15(Event event) {
        b13.setTextFill(Color.GOLD);
        r14.setTextFill(Color.GOLD);
        b15.setTextFill(Color.GOLD);

    }

    public void disable_13_14_15(Event event) {
        b13.setTextFill(Color.WHITE);
        r14.setTextFill(Color.WHITE);
        b15.setTextFill(Color.WHITE);

    }

    public void actvie_11_12(Event event) {
        b11.setTextFill(Color.GOLD);
        r12.setTextFill(Color.GOLD);

    }

    public void disable_11_12(Event event) {
        b11.setTextFill(Color.WHITE);
        r12.setTextFill(Color.WHITE);

    }

    public void actvie_10_11(Event event) {
        b10.setTextFill(Color.GOLD);
        b11.setTextFill(Color.GOLD);

    }

    public void disable_10_11(Event event) {
        b10.setTextFill(Color.WHITE);
        b11.setTextFill(Color.WHITE);

    }

    public void actvie_10_13(Event event) {
        b10.setTextFill(Color.GOLD);
        b13.setTextFill(Color.GOLD);

    }

    public void disable_10_13(Event event) {
        b10.setTextFill(Color.WHITE);
        b13.setTextFill(Color.WHITE);

    }

    public void actvie_11_14(Event event) {
        b11.setTextFill(Color.GOLD);
        r14.setTextFill(Color.GOLD);

    }

    public void disable_11_14(Event event) {
        b11.setTextFill(Color.WHITE);
        r14.setTextFill(Color.WHITE);

    }

    public void actvie_12_15(Event event) {
        r12.setTextFill(Color.GOLD);
        b15.setTextFill(Color.GOLD);


    }

    public void disable_12_15(Event event) {
        r12.setTextFill(Color.WHITE);
        b15.setTextFill(Color.WHITE);

    }

    public void active_11_12_14_15(Event event) {
        b11.setTextFill(Color.GOLD);
        r12.setTextFill(Color.GOLD);
        r14.setTextFill(Color.GOLD);
        b15.setTextFill(Color.GOLD);

    }

    public void disable_11_12_14_15(Event event) {
        b11.setTextFill(Color.WHITE);
        r12.setTextFill(Color.WHITE);
        r14.setTextFill(Color.WHITE);
        b15.setTextFill(Color.WHITE);

    }

    public void active_10_11_13_14(Event event) {
        b10.setTextFill(Color.GOLD);
        b11.setTextFill(Color.GOLD);
        b13.setTextFill(Color.GOLD);
        r14.setTextFill(Color.GOLD);

    }

    public void disable_10_11_13_14(Event event) {
        b10.setTextFill(Color.WHITE);
        b11.setTextFill(Color.WHITE);
        b13.setTextFill(Color.WHITE);
        r14.setTextFill(Color.WHITE);

    }

    public void active_10_11_12(Event event) {
        b10.setTextFill(Color.GOLD);
        b11.setTextFill(Color.GOLD);
        r12.setTextFill(Color.GOLD);

    }

    public void disable_10_11_12(Event event) {
        b10.setTextFill(Color.WHITE);
        b11.setTextFill(Color.WHITE);
        r12.setTextFill(Color.WHITE);

    }

    public void actvie_8_9(Event event) {
        b8.setTextFill(Color.GOLD);
        r9.setTextFill(Color.GOLD);

    }

    public void disable_8_9(Event event) {
        b8.setTextFill(Color.WHITE);
        r9.setTextFill(Color.WHITE);

    }

    public void actvie_7_8(Event event) {
        r7.setTextFill(Color.GOLD);
        b8.setTextFill(Color.GOLD);

    }

    public void disable_7_8(Event event) {
        r7.setTextFill(Color.WHITE);
        b8.setTextFill(Color.WHITE);

    }

    public void actvie_7_10(Event event) {
        r7.setTextFill(Color.GOLD);
        b10.setTextFill(Color.GOLD);

    }

    public void disable_7_10(Event event) {
        r7.setTextFill(Color.WHITE);
        b10.setTextFill(Color.WHITE);

    }

    public void actvie_8_11(Event event) {
        b8.setTextFill(Color.GOLD);
        b11.setTextFill(Color.GOLD);

    }

    public void disable_8_11(Event event) {
        b8.setTextFill(Color.WHITE);
        b11.setTextFill(Color.WHITE);

    }

    public void actvie_9_12(Event event) {
        r9.setTextFill(Color.GOLD);
        r12.setTextFill(Color.GOLD);

    }

    public void disable_9_12(Event event) {
        r9.setTextFill(Color.WHITE);
        r12.setTextFill(Color.WHITE);

    }

    public void active_8_9_11_12(Event event) {
        b8.setTextFill(Color.GOLD);
        r9.setTextFill(Color.GOLD);
        b11.setTextFill(Color.GOLD);
        r12.setTextFill(Color.GOLD);

    }

    public void disable_8_9_11_12(Event event) {
        b8.setTextFill(Color.WHITE);
        r9.setTextFill(Color.WHITE);
        b11.setTextFill(Color.WHITE);
        r12.setTextFill(Color.WHITE);

    }

    public void active_7_8_10_11(Event event) {
        r7.setTextFill(Color.GOLD);
        b8.setTextFill(Color.GOLD);
        b10.setTextFill(Color.GOLD);
        b11.setTextFill(Color.GOLD);

    }

    public void disable_7_8_10_11(Event event) {

        r7.setTextFill(Color.WHITE);
        b8.setTextFill(Color.WHITE);
        b10.setTextFill(Color.WHITE);
        b11.setTextFill(Color.WHITE);

    }

    public void active_7_8_9(Event event) {
        r7.setTextFill(Color.GOLD);
        b8.setTextFill(Color.GOLD);
        r9.setTextFill(Color.GOLD);

    }

    public void disable_7_8_9(Event event) {
        r7.setTextFill(Color.WHITE);
        b8.setTextFill(Color.WHITE);
        r9.setTextFill(Color.WHITE);

    }

    public void actvie_5_6(Event event) {
        r5.setTextFill(Color.GOLD);
        b6.setTextFill(Color.GOLD);

    }

    public void disable_5_6(Event event) {
        r5.setTextFill(Color.WHITE);
        b6.setTextFill(Color.WHITE);

    }

    public void actvie_4_5(Event event) {
        b4.setTextFill(Color.GOLD);
        r5.setTextFill(Color.GOLD);

    }

    public void disable_4_5(Event event) {
        b4.setTextFill(Color.WHITE);
        r5.setTextFill(Color.WHITE);

    }

    public void actvie_4_7(Event event) {
        b4.setTextFill(Color.GOLD);
        r7.setTextFill(Color.GOLD);

    }

    public void disable_4_7(Event event) {
        b4.setTextFill(Color.WHITE);
        r7.setTextFill(Color.WHITE);

    }

    public void actvie_5_8(Event event) {
        r5.setTextFill(Color.GOLD);
        b8.setTextFill(Color.GOLD);

    }

    public void disable_5_8(Event event) {
        r5.setTextFill(Color.WHITE);
        b8.setTextFill(Color.WHITE);

    }

    public void actvie_6_9(Event event) {
        b6.setTextFill(Color.GOLD);
        r9.setTextFill(Color.GOLD);

    }

    public void disable_6_9(Event event) {
        b6.setTextFill(Color.WHITE);
        r9.setTextFill(Color.WHITE);

    }

    public void active_5_6_8_9(Event event) {
        r5.setTextFill(Color.GOLD);
        b6.setTextFill(Color.GOLD);
        b8.setTextFill(Color.GOLD);
        r9.setTextFill(Color.GOLD);


    }

    public void disable_5_6_8_9(Event event) {
        r5.setTextFill(Color.WHITE);
        b6.setTextFill(Color.WHITE);
        b8.setTextFill(Color.WHITE);
        r9.setTextFill(Color.WHITE);

    }

    public void active_4_5_7_8(Event event) {
        b4.setTextFill(Color.GOLD);
        r5.setTextFill(Color.GOLD);
        r7.setTextFill(Color.GOLD);
        b8.setTextFill(Color.GOLD);

    }

    public void disable_4_5_7_8(Event event) {
        b4.setTextFill(Color.WHITE);
        r5.setTextFill(Color.WHITE);
        r7.setTextFill(Color.WHITE);
        b8.setTextFill(Color.WHITE);

    }

    public void active_4_5_6(Event event) {
        b4.setTextFill(Color.GOLD);
        r5.setTextFill(Color.GOLD);
        b6.setTextFill(Color.GOLD);

    }

    public void disable_4_5_6(Event event) {
        b4.setTextFill(Color.WHITE);
        r5.setTextFill(Color.WHITE);
        b6.setTextFill(Color.WHITE);

    }

    public void actvie_2_3(Event event) {
        b2.setTextFill(Color.GOLD);
        r3.setTextFill(Color.GOLD);

    }

    public void disable_2_3(Event event) {
        b2.setTextFill(Color.WHITE);
        r3.setTextFill(Color.WHITE);

    }

    public void actvie_1_2(Event event) {
        r1.setTextFill(Color.GOLD);
        b2.setTextFill(Color.GOLD);

    }

    public void disable_1_2(Event event) {
        r1.setTextFill(Color.WHITE);
        b2.setTextFill(Color.WHITE);

    }

    public void actvie_1_4(Event event) {
        r1.setTextFill(Color.GOLD);
        b4.setTextFill(Color.GOLD);

    }

    public void disable_1_4(Event event) {
        r1.setTextFill(Color.WHITE);
        b4.setTextFill(Color.WHITE);

    }

    public void actvie_2_5(Event event) {
        b2.setTextFill(Color.GOLD);
        r5.setTextFill(Color.GOLD);

    }

    public void disable_2_5(Event event) {
        b2.setTextFill(Color.WHITE);
        r5.setTextFill(Color.WHITE);

    }

    public void active_1_2_3(Event event) {
        r1.setTextFill(Color.GOLD);
        b2.setTextFill(Color.GOLD);
        r3.setTextFill(Color.GOLD);

    }

    public void disable_1_2_3(Event event) {
        r1.setTextFill(Color.WHITE);
        b2.setTextFill(Color.WHITE);
        r3.setTextFill(Color.WHITE);

    }

    public void active_0_1(Event event) {
        zer0.setTextFill(Color.GOLD);
        r1.setTextFill(Color.GOLD);

    }

    public void disable_0_1(Event event) {
        zer0.setTextFill(Color.WHITE);
        r1.setTextFill(Color.WHITE);

    }

    public void active_0_2(Event event) {
        zer0.setTextFill(Color.GOLD);
        b2.setTextFill(Color.GOLD);

    }

    public void disable_0_2(Event event) {
        zer0.setTextFill(Color.WHITE);
        b2.setTextFill(Color.WHITE);

    }

    public void active_0_3(Event event) {
        zer0.setTextFill(Color.GOLD);
        r3.setTextFill(Color.GOLD);

    }

    public void disable_0_3(Event event) {
        zer0.setTextFill(Color.WHITE);
        r3.setTextFill(Color.WHITE);

    }


    //multy bets


    public void bet_36_35(ActionEvent event) {

    }

    public void bet_35_34(ActionEvent event) {

    }

    public void bet_34_35_36(ActionEvent event) {

    }

    public void bet_33_32(ActionEvent event) {

    }

    public void bet_32_31(ActionEvent event) {

    }


    public void bet_31_34(ActionEvent event) {

    }

    public void bet_32_35(ActionEvent event) {

    }

    public void bet_33_36(ActionEvent event) {

    }

    public void bet_32_33_35_36(ActionEvent event) {

    }

    public void bet_31_32_34_35(ActionEvent event) {

    }

    public void bet_31_32_33(ActionEvent event) {

    }

    public void bet_31_32_33_34_35_36(ActionEvent event) {

    }

    public void bet_30_29(ActionEvent event) {

    }

    public void bet_29_28(ActionEvent event) {

    }

    public void bet_28_31(ActionEvent event) {

    }

    public void bet_29_32(ActionEvent event) {

    }

    public void bet_30_33(ActionEvent event) {

    }

    public void bet_29_30_32_33(ActionEvent event) {

    }

    public void bet_28_29_31_32(ActionEvent event) {

    }

    public void bet_28_29_30(ActionEvent event) {

    }

    public void bet_28_29_30_31_32_33(ActionEvent event) {

    }

    public void bet_27_26(ActionEvent event) {

    }

    public void bet_26_25(ActionEvent event) {

    }

    public void bet_25_28(ActionEvent event) {

    }

    public void bet_26_29(ActionEvent event) {

    }

    public void bet_27_30(ActionEvent event) {

    }

    public void bet_26_27_29_30(ActionEvent event) {

    }

    public void bet_25_26_28_29(ActionEvent event) {

    }

    public void bet_25_26_27(ActionEvent event) {

    }

    public void bet_25_26_27_28_29_30(ActionEvent event) {

    }

    public void bet_24_23(ActionEvent event) {

    }

    public void bet_23_22(ActionEvent event) {

    }

    public void bet_22_25(ActionEvent event) {

    }

    public void bet_23_26(ActionEvent event) {

    }

    public void bet_24_27(ActionEvent event) {

    }

    public void bet_23_24_26_27(ActionEvent event) {

    }

    public void bet_22_23_25_26(ActionEvent event) {

    }

    public void bet_22_23_24(ActionEvent event) {

    }

    public void bet_22_23_24_25_26_27(ActionEvent event) {

    }

    public void bet_21_20(ActionEvent event) {

    }

    public void bet_20_19(ActionEvent event) {

    }

    public void bet_19_22(ActionEvent event) {

    }

    public void bet_20_23(ActionEvent event) {

    }

    public void bet_21_24(ActionEvent event) {

    }

    public void bet_20_21_23_24(ActionEvent event) {

    }

    public void bet_19_20_22_23(ActionEvent event) {

    }

    public void bet_19_20_21(ActionEvent event) {

    }

    public void bet_19_20_21_22_23_24(ActionEvent event) {

    }

    public void bet_18_17(ActionEvent event) {

    }

    public void bet_17_16(ActionEvent event) {

    }

    public void bet_16_19(ActionEvent event) {

    }

    public void bet_17_20(ActionEvent event) {

    }

    public void bet_18_21(ActionEvent event) {

    }

    public void bet_17_18_20_21(ActionEvent event) {

    }

    public void bet_16_17_19_20(ActionEvent event) {

    }

    public void bet_16_17_18(ActionEvent event) {

    }

    public void bet_16_17_18_19_20_21(ActionEvent event) {

    }

    public void bet_15_14(ActionEvent event) {

    }

    public void bet_14_13(ActionEvent event) {

    }

    public void bet_13_16(ActionEvent event) {

    }

    public void bet_14_17(ActionEvent event) {

    }

    public void bet_15_18(ActionEvent event) {

    }

    public void bet_14_15_17_18(ActionEvent event) {

    }

    public void bet_13_14_16_17(ActionEvent event) {

    }

    public void bet_13_14_15(ActionEvent event) {

    }

    public void bet_13_14_15_16_17_18(ActionEvent event) {

    }

    public void bet_12_11(ActionEvent event) {

    }

    public void bet_11_10(ActionEvent event) {

    }

    public void bet_10_13(ActionEvent event) {

    }

    public void bet_11_14(ActionEvent event) {

    }

    public void bet_12_15(ActionEvent event) {

    }

    public void bet_11_12_14_15(ActionEvent event) {

    }

    public void bet_10_11_13_14(ActionEvent event) {

    }

    public void bet_10_11_12(ActionEvent event) {

    }

    public void bet_10_11_12_13_14_15(ActionEvent event) {

    }

    public void bet_8_9(ActionEvent event) {

    }

    public void bet_7_8(ActionEvent event) {

    }

    public void bet_7_10(ActionEvent event) {

    }

    public void bet_8_11(ActionEvent event) {

    }

    public void bet_9_12(ActionEvent event) {

    }

    public void bet_8_9_11_12(ActionEvent event) {

    }

    public void bet_7_8_10_11(ActionEvent event) {

    }

    public void bet_7_8_9(ActionEvent event) {

    }

    public void bet_7_8_9_10_11_12(ActionEvent event) {

    }

    public void bet_5_6(ActionEvent event) {

    }

    public void bet_4_5(ActionEvent event) {

    }

    public void bet_4_7(ActionEvent event) {

    }

    public void bet_5_8(ActionEvent event) {

    }

    public void bet_6_9(ActionEvent event) {

    }

    public void bet_5_6_8_9(ActionEvent event) {

    }

    public void bet_4_5_7_8(ActionEvent event) {

    }

    public void bet_4_5_6(ActionEvent event) {

    }

    public void bet_4_5_6_7_8_9(ActionEvent event) {

    }

    public void bet_0_1(ActionEvent event) {

    }

    public void bet_2_3(ActionEvent event) {

    }

    public void bet_1_2(ActionEvent event) {

    }

    public void bet_1_4(ActionEvent event) {

    }

    public void bet_2_5(ActionEvent event) {

    }

    public void bet_3_6(ActionEvent event) {

    }

    public void bet_2_3_5_6(ActionEvent event) {

    }

    public void bet_1_2_4_5(ActionEvent event) {

    }

    public void bet_0_2(ActionEvent event) {

    }

    public void bet_0_3(ActionEvent event) {
        betPlace.clear();
        betPlace.add(0);
        betPlace.add(3);
        startDonateWindow(event);
    }

    public void bet_0_2_1(ActionEvent event) {
        betPlace.clear();
        for (int i = 0; i <= 2; i++) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void bet_0_2_3(ActionEvent event) {
        betPlace.clear();
        betPlace.add(0);
        for (int i = 2; i <= 3; i++) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void bet_1_2_3(ActionEvent event) {
        betPlace.clear();
        for (int i = 1; i <= 3; i++) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void bet_1_2_3_4_5_6(ActionEvent event) {
        betPlace.clear();
        for (int i = 1; i <= 6; i++) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void bet_0_1_2_3(ActionEvent event) {
        betPlace.clear();
        for (int i = 0; i <= 3; i++) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void bet_f_h(ActionEvent event) {
        betPlace.clear();
        for (int i = 1; i <= 18; i++) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void bet_even(ActionEvent event) {
        betPlace.clear();
        for (int i = 0; i < 37; i += 2) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void betf_12(ActionEvent event) {
        betPlace.clear();
        for (int i = 1; i < 13; i++) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void bet_s_12(ActionEvent event) {
        betPlace.clear();
        for (int i = 13; i < 25; i++) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void bet_t_12(ActionEvent event) {
        betPlace.clear();
        for (int i = 25; i < 37; i++) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void bet_red(ActionEvent event) {
        betPlace.clear();
        for (int i = 1; i <= 9; i += 2) {
            betPlace.add(i);
        }
        for (int i = 12; i <= 18; i += 2) {
            betPlace.add(i);
        }
        for (int i = 19; i <= 27; i += 2) {
            betPlace.add(i);
        }
        for (int i = 30; i <= 36; i += 2) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void bet_black(ActionEvent event) {
        betPlace.clear();
        for (int i = 2; i <= 10; i += 2) {
            betPlace.add(i);
        }
        for (int i = 11; i <= 17; i += 2) {
            betPlace.add(i);
        }
        for (int i = 20; i <= 28; i += 2) {
            betPlace.add(i);
        }
        for (int i = 29; i <= 35; i += 2) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void bet_odd(ActionEvent event) {
        betPlace.clear();
        for (int i = 1; i < 36; i += 2) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void bet_s_h(ActionEvent event) {
        betPlace.clear();
        for (int i = 19; i <= 36; i++) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void bet_zer0(ActionEvent event) {
        betPlace.clear();
        betPlace.add(0);
        startDonateWindow(event);
    }

    public void bet_top_12(ActionEvent event) {
        betPlace.clear();
        for (int i = 3; i < 37; i += 3) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void bet_middle_12(ActionEvent event) {
        betPlace.clear();
        for (int i = 2; i < 36; i += 3) {
            betPlace.add(i);
        }
        startDonateWindow(event);
    }

    public void bet_bottom_12(ActionEvent event) {
        betPlace.clear();
        for (int i = 1; i < 35; i += 3) {
            betPlace.add(i);
        }
        startDonateWindow(event);


    }
}
