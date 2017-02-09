package com.nwu.client;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Activity implements Initializable {

   public static TextArea textField = new TextArea();

   public Activity() {
      textField.setEditable(false);
   }

   public void appendText(String str) {
      Platform.runLater(() -> textField.appendText(str));
   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      OutputStream out = new OutputStream() {
         @Override
         public void write(int b) throws IOException {
            appendText(String.valueOf((char) b));
         }
      };
      System.setOut(new PrintStream(out, true));
   }
}
