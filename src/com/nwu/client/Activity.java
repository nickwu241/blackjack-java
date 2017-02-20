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
   private static TextArea textArea_;

   public static void setTextArea(TextArea textField) {
      Activity.textArea_ = textField;
   }

   public static void appendText(String str) {
      Platform.runLater(() -> textArea_.appendText(str));
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
