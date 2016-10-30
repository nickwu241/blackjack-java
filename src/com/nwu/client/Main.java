package com.nwu.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import com.nwu.fundementals.BlackjackTable;
import com.nwu.fundementals.HumanPlayer;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.*;

public class Main extends Application
{
   String input = "";

   public static void main(String[] args)
   {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws IOException
   {
      // Creating a GridPane container
      GridPane grid = new GridPane();
      grid.setPadding(new Insets(10, 10, 10, 10));
      grid.setVgap(5);
      grid.setHgap(5);
      // Defining the Name text field
      final TextField name = new TextField();
      name.setPromptText("Enter your name");
      name.setPrefColumnCount(10);
      name.getText();
      GridPane.setConstraints(name, 0, 0);
      grid.getChildren().add(name);
      // Defining the Submit button
      Button submit = new Button("Submit");
      GridPane.setConstraints(submit, 1, 0);
      grid.getChildren().add(submit);
      // Adding a Label
      final Label label = new Label();
      GridPane.setConstraints(label, 0, 1);
      grid.getChildren().add(label);
      // Defining Hit, Stay, Double Down, Split buttons
      HBox hb = new HBox();
      Button hit = new Button("Hit");
      Button stay = new Button("Stay");
      Button dd = new Button("Double Down");
      Button split = new Button("Split");
      hb.getChildren().addAll(hit, stay, dd, split);
      GridPane.setConstraints(hb, 0, 2);
      grid.getChildren().add(hb);
      // Defining the TextArea
      TextArea ta = new TextArea();
      Console console = new Console(ta);
      PrintStream ps = new PrintStream(console, true);
      System.setOut(ps);
      System.setErr(ps);
      GridPane.setConstraints(ta, 0, 3);
      grid.getChildren().add(ta);

      // String input = "";
      // Setting an action for the Submit button
      submit.setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent e)
         {
            if ((name.getText() != null && !name.getText().isEmpty()))
            {
               input = name.getText();
               label.setText("Hi " + name.getText());
            }
         }
      });

      Task<Void> sleeper = new Task<Void>() {
         @Override
         protected Void call() throws Exception {
             try {
                 Thread.sleep(5000);
             } catch (InterruptedException e) {
             }
             return null;
         }
     };
     sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
         @Override
         public void handle(WorkerStateEvent event) {
             label.setText("Hello World");
         }
     });
     new Thread(sleeper).start();
     
      // Start
      Scene app = new Scene(grid);

      primaryStage.setScene(app);
      primaryStage.show();

      BlackjackTable table = new BlackjackTable(10);
      System.out.println("Enter you name: ");
      System.out.println("Okay " + input + ", you have $1000");
      HumanPlayer player = new HumanPlayer("ID", input);
      player.setMoney(1000);
      table.addPlayer(player);
      //table.play();

      ps.close();
   }

   public static class Console extends OutputStream
   {
      private TextArea output;

      public Console(TextArea ta)
      {
         this.output = ta;
      }

      @Override
      public void write(int i) throws IOException
      {
         output.appendText(String.valueOf((char) i));
      }
   }
}
