package com.nwu.client.view;

import com.nwu.backend.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.text.NumberFormat;
import java.util.regex.Pattern;

import static com.nwu.backend.BlackjackPlayer.Action;

public class MainViewController {
   // TODO: currently max cards cap @ showing 8 cards
   // TODO: use better card designs then enable the casino_mat.jpg imageview
   // TODO: add alerts rather than have them in the activty area
   // TODO: have way to show all your split hands?

   private static final String kERR_INAVALID_BET_AMOUNT = "Hey! Be nice and enter a valid money amount in the BET section!";
   private static final Pattern kMONEY_PATTERN = Pattern.compile("\\d+(\\.\\d\\d)?");

   private static final ImageHelper imageHelper = ImageHelper.instance();
   private static final NumberFormat cFormatter = NumberFormat.getCurrencyInstance();

   private static final BlackjackSystem system = new SimpleBlackjackSystem();
   private static BlackjackGame game;
   private static BlackjackPlayer player;

   private static ImageView[] dealerImgvs;
   private static ImageView[] playerImgvs;

   @FXML private AnchorPane mainLayout;
   @FXML private ImageView dc1, dc2, dc3, dc4, dc5, dc6, dc7, dc8;
   @FXML private ImageView pc1, pc2, pc3, pc4, pc5, pc6, pc7, pc8;

   @FXML private TextArea activityArea;
   @FXML private Button hit, stay, doubleDown, split, bet;
   @FXML private Label minWager, playerMoney, systemAlert;
   @FXML private TextField betAmount;

   public static void setUp (BlackjackGame bjGame, BlackjackPlayer bjPlayer) {
      game = bjGame;
      player = bjPlayer;
   }

   @FXML
   private void initialize () {
      dealerImgvs = new ImageView[]{dc1, dc2, dc3, dc4, dc5, dc6, dc7, dc8};
      playerImgvs = new ImageView[]{pc1, pc2, pc3, pc4, pc5, pc6, pc7, pc8};

      minWager.setText(cFormatter.format(game.getMinWager()));
      Activity.setTextArea(activityArea);
      Activity.setAlertArea(systemAlert);

      mainLayout.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
      bet.setOnAction(this::handleButtonAction);
      hit.setOnAction(this::handleButtonAction);
      stay.setOnAction(this::handleButtonAction);
      doubleDown.setOnAction(this::handleButtonAction);
      split.setOnAction(this::handleButtonAction);

      Timeline timeline = new Timeline();
      timeline.setCycleCount(Timeline.INDEFINITE);
      timeline.getKeyFrames()
              .add(new KeyFrame(Duration.millis(500), e -> update()));
      timeline.playFromStart();
   }

   private void update () {
      playerMoney.setText(cFormatter.format(player.getMoney()));
      displayHands();
      displayButtonsState();
   }

   private void displayHands () {
      imagesToViews(game.getDealerHand()
                        .stream()
                        .map(imageHelper::imageForCard)
                        .toArray(Image[]::new),
                    dealerImgvs);

      imagesToViews(game.getCurrentHand()
                        .stream()
                        .map(imageHelper::imageForCard)
                        .toArray(Image[]::new),
                    playerImgvs);
   }

   /**
    * Helper for displayHands()
    *
    * @param in
    * @param out
    */
   private void imagesToViews (final Image[] in, ImageView[] out) {
      // display all images
      for (int i = 0; i < in.length; i++) {
         out[i].setImage(in[i]);
      }
      // clear out unused image slots
      for (int i = in.length; i < out.length; i++) {
         out[i].setImage(null);
      }
   }

   private void displayButtonsState () {
      bet.setDisable(!game.getBetAvailable());
      betAmount.setDisable(!game.getBetAvailable());

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

   private void handleButtonAction (ActionEvent e) {
      Object src = e.getSource();
      if (src == bet) {
         if (!kMONEY_PATTERN.matcher(betAmount.getText()).matches()) {
            system.out(kERR_INAVALID_BET_AMOUNT, BlackjackHelper.log(), BlackjackHelper.alert());
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

   private void handleKeyPressed (KeyEvent e) {
      KeyCode c = e.getCode();
      if (c == KeyCode.TAB || c == KeyCode.BACK_SPACE) {
         // TODO: learn a better way to handle these key inputs
         // propagate these events
         return;
      }
      else if (c == KeyCode.ENTER) {
         if (!bet.isDisabled()) {
            bet.fire();
         }
         else {
            mainLayout.getScene().getFocusOwner().fireEvent(new ActionEvent());
         }
      }
      else if (c == KeyCode.H && !hit.isDisabled()) {
         hit.fire();
      }
      else if (c == KeyCode.S && !stay.isDisabled()) {
         stay.fire();
      }
      else if (c == KeyCode.D && !doubleDown.isDisabled()) {
         doubleDown.fire();
      }
      else if (c == KeyCode.T && !split.isDisabled()) {
         split.fire();
      }
      e.consume();
   }

}
