package com.nwu.client;

import com.nwu.fundementals.Action;
import com.nwu.fundementals.GenericPlayer;
import com.nwu.fundementals.Table;
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
import java.util.ArrayList;

public class Main extends Application {
   private Table table;
   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws IOException {
      Scene app = new Scene(mainScene());
      Activity text = new Activity();
      primaryStage.setScene(app);
      primaryStage.show();

      ArrayList<GenericPlayer> players = new ArrayList<>();
      // TODO Random ID
      players.add(new GenericPlayer("random", "Nick"));
      players.get(0).setMoney(1000);
      table = new Table(10, players);
      table.setActivity(text);
   }


   public GridPane mainScene() {
      // main grid
      final GridPane grid = new GridPane();
      grid.setPadding(new Insets(10, 10, 10, 10));
      grid.setVgap(5);
      grid.setHgap(5);

      final TextField name = new TextField();
      name.setPromptText("Enter your name");
      name.setPrefColumnCount(10);
      final Button submit = new Button("Submit");
      final Button play = new Button("Play");

      final HBox buttons = new HBox();
      final Button hit = new Button("Hit");
      final Button stay = new Button("Stay");
      final Button dd = new Button("Double Down");
      final Button split = new Button("Split");
      buttons.getChildren().addAll(hit, stay, dd, split);

      // Events
      // TODO: add submit feature
      submit.setOnAction(event -> {});
      play.setOnAction(event -> new Thread(() -> table.newRound()).start());
      hit.setOnAction(event -> table.setAction(Action.HIT));
      stay.setOnAction(event -> table.setAction(Action.STAY));
      dd.setOnAction(event -> table.setAction(Action.DOUBLEDOWN));
      split.setOnAction(event -> table.setAction(Action.SPLIT));

      // Positioning
      GridPane.setConstraints(name, 0, 0);
      GridPane.setConstraints(Activity.textField, 0, 1);
      GridPane.setConstraints(buttons, 0,2);
      GridPane.setConstraints(submit, 1, 0);
      GridPane.setConstraints(play, 1, 1);

      grid.getChildren().addAll(name, Activity.textField, buttons, play, submit);
      return grid;
   }
}
