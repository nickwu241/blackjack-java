package com.nwu.client.view;

import com.nwu.backend.BlackjackGame;
import com.nwu.backend.BlackjackPlayer;
import com.nwu.backend.BlackjackSystem;
import com.nwu.backend.SimpleBlackjackSystem;
import com.nwu.client.Activity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.text.NumberFormat;
import java.util.regex.Pattern;

import static com.nwu.backend.BlackjackPlayer.Action;

public class MainViewController {
   private static final String kERR_INAVALID_BET_AMOUNT = "Hey! Be nice and enter a valid money amount in the BET section!";

   private static final Pattern kMONEY_PATTERN = Pattern.compile("\\d+(\\.\\d\\d)?");

   private static final NumberFormat cFormatter = NumberFormat.getCurrencyInstance();

   private static final BlackjackSystem system = new SimpleBlackjackSystem();
   private static BlackjackGame game;
   private static BlackjackPlayer player;

   public static void setUp(BlackjackGame bjGame, BlackjackPlayer bjPlayer) {
      game = bjGame;
      player = bjPlayer;
   }

   @FXML
   private TextArea activityArea;

   @FXML
   private Button hit, stay, doubleDown, split, bet;

   @FXML
   private Label minWager, playerMoney;

   @FXML
   private TextField betAmount;

   @FXML
   private void initialize() {
      Activity.setTextArea(activityArea);

      bet.setOnAction(e -> handleButtonAction(e));

      hit.setOnAction(e -> handleButtonAction(e));
      stay.setOnAction(e -> handleButtonAction(e));
      doubleDown.setOnAction(e -> handleButtonAction(e));
      split.setOnAction(e -> handleButtonAction(e));

      minWager.setText(cFormatter.format(game.getMinWager()));

      Timeline timeline = new Timeline();
      timeline.setCycleCount(Timeline.INDEFINITE);
      timeline.getKeyFrames()
              .add(new KeyFrame(Duration.millis(100), e -> update()));
      timeline.playFromStart();
   }

   private void update() {
      playerMoney.setText(cFormatter.format(player.getMoney()));
      displayButtonsState();
   }

   private void displayButtonsState() {
      bet.setDisable(!game.getBetAvailable());

      // default values of bool array are false
      boolean buttonsOn[] = new boolean[4];

      game.getActionsAvailable().forEach(a -> {
         if (a == Action.HIT) buttonsOn[0] = true;
         else if (a == Action.STAY) buttonsOn[1] = true;
         else if (a == Action.DOUBLEDOWN) buttonsOn[2] = true;
         else if (a == Action.SPLIT) buttonsOn[3] = true;
      });

      hit.setDisable(!buttonsOn[0]);
      stay.setDisable(!buttonsOn[1]);
      doubleDown.setDisable(!buttonsOn[2]);
      split.setDisable(!buttonsOn[3]);
   }

   private void handleButtonAction(ActionEvent e) {
      Object src = e.getSource();
      if (src == bet) {
         if (!kMONEY_PATTERN.matcher(betAmount.getText()).matches()) {
            system.out(kERR_INAVALID_BET_AMOUNT);
            return;
         }
         try {
            game.setBet(Double.valueOf(betAmount.getText()));
         }
         catch (NumberFormatException ex) {
            // Shouldn't get here since we regex to check for valid double
            assert (false);
         }
      }
      else if (src == hit) game.setAction(Action.HIT);
      else if (src == stay) game.setAction(Action.STAY);
      else if (src == doubleDown) game.setAction(Action.DOUBLEDOWN);
      else if (src == split) game.setAction(Action.SPLIT);
   }
}
