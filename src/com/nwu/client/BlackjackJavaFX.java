package com.nwu.client;

import com.nwu.backend.BlackjackGame;
import com.nwu.backend.BlackjackPlayer;
import com.nwu.client.view.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BlackjackJavaFX extends Application {
   private final int kMIN_WAGER = 10;
   private final int kSTARTING_PLAYER_MONEY = 100;
   private final String kSTAGE_TITLE = "NICK'S FANCY BLACKJACK CASINO";

   private Stage primaryStage;
   private BlackjackGame game;
   private BlackjackPlayer player;

   @Override
   public void start(Stage primaryStage) throws IOException {
      // Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
      this.primaryStage = primaryStage;
      this.primaryStage.setTitle(kSTAGE_TITLE);

      // TODO: ask for player's name
      player = new BlackjackPlayer("Nick").setMoney(kSTARTING_PLAYER_MONEY);
      game = new BlackjackGame(player, kMIN_WAGER);
      MainViewController.setUp(game, player);

      showMainView();

      Thread gameBackend = new Thread(() -> {
         while (player.getMoney() > 0)
            game.play();
      });
      gameBackend.setDaemon(true);
      gameBackend.start();
   }

   private void showMainView() throws IOException {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(BlackjackJavaFX.class.getResource("view/MainView.fxml"));
      primaryStage.setScene(new Scene(loader.load()));
      primaryStage.show();
   }

   public static void main(String[] args) {
      launch(args);
   }

}
