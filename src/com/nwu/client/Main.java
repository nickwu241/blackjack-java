package com.nwu.client;

import com.nwu.fundementals.BlackjackGame;
import com.nwu.fundementals.BlackjackPlayer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

import static com.nwu.fundementals.BlackjackPlayer.Action;

public class Main extends Application {
   private BlackjackGame table;
   private BlackjackPlayer player;

   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws IOException {
      Scene app = new Scene(mainScene());
      primaryStage.setScene(app);
      primaryStage.show();

      table = new BlackjackGame(player, 10);
      new Thread(() -> {
         while (player.getMoney() > 0)
            table.play();
      }).start();
   }


   public GridPane mainScene() {
      player = new BlackjackPlayer("Nick").setMoney(100);
      // Main Grid
      final GridPane grid = new GridPane();
      grid.setPadding(new Insets(10, 10, 10, 10));
      grid.setVgap(5);
      grid.setHgap(5);

      final TextField name = new TextField();
      name.setPromptText("Enter your name");
      name.setPrefColumnCount(10);
      final Button submit = new Button("Submit");
      final Button seeMoney = new Button("See Money");

      final HBox buttons = new HBox();
      final Button hit = new Button("Hit");
      final Button stay = new Button("Stay");
      final Button dd = new Button("Double Down");
      final Button split = new Button("Split");

      final Label label = new Label();

      buttons.getChildren().addAll(hit, stay, dd, split);

      // Events
      // TODO: add submit feature
      seeMoney.setOnAction(event -> label.setText(String.valueOf(player.getMoney())));
      hit.setOnAction(event -> table.setAction(Action.HIT));
      stay.setOnAction(event -> table.setAction(Action.STAY));
      dd.setOnAction(event -> table.setAction(Action.DOUBLEDOWN));
      split.setOnAction(event -> table.setAction(Action.SPLIT));

      // Positioning
      GridPane.setConstraints(name, 0, 0);
      GridPane.setConstraints(Activity.textField, 0, 1);
      GridPane.setConstraints(buttons, 0, 2);
      GridPane.setConstraints(submit, 1, 0);
      GridPane.setConstraints(seeMoney, 1, 1);
      GridPane.setConstraints(label, 0, 3);

      grid.getChildren()
          .addAll(name, Activity.textField, buttons, seeMoney, submit, label);
      return grid;
   }
}
