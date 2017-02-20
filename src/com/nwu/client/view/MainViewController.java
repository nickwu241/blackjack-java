package com.nwu.client.view;

import com.nwu.backend.BlackjackGame;
import com.nwu.backend.BlackjackPlayer;
import com.nwu.client.Activity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.Set;

import static com.nwu.backend.BlackjackPlayer.Action;

public class MainViewController {

   private static IntegerProperty playerMoneyIntegerProperty;
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

      hit.setOnAction(e -> game.setAction(Action.HIT));
      stay.setOnAction(e -> game.setAction(Action.STAY));
      doubleDown.setOnAction(e -> game.setAction(Action.DOUBLEDOWN));
      split.setOnAction(e -> game.setAction(Action.SPLIT));

      bet.setOnAction(e -> game.setBet(Integer.valueOf(betAmount.getText())));

      playerMoneyIntegerProperty = new SimpleIntegerProperty(0);
      playerMoney.textProperty()
                 .bind(Bindings.convert(playerMoneyIntegerProperty));

      minWager.setText(String.valueOf(game.getMinWager()));

      Timeline timeline = new Timeline();
      timeline.setCycleCount(Timeline.INDEFINITE);
      timeline.getKeyFrames()
              .add(new KeyFrame(Duration.millis(100), e -> update()));
      timeline.playFromStart();
   }

   private void update() {
      playerMoneyIntegerProperty.set(player.getMoney());
      displayButtonsState();
   }

   private void displayButtonsState() {
      bet.setDisable(!game.getBetAvailable());

      hit.setDisable(true);
      stay.setDisable(true);
      doubleDown.setDisable(true);
      split.setDisable(true);

      game.getActionsAvailable().forEach(action -> {
         if (action == Action.HIT) hit.setDisable(false);
         else if (action == Action.STAY) stay.setDisable(false);
         else if (action == Action.DOUBLEDOWN) doubleDown.setDisable(false);
         else if (action == Action.SPLIT) split.setDisable(false);
      });
   }

}
