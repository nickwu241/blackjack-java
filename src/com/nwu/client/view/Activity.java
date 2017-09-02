package com.nwu.client.view;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Activity implements Initializable {
   private static TextArea textArea_;
   private static Label alertAera_;

   public static void setTextArea (TextArea textField) {
      Activity.textArea_ = textField;
   }

   public static void setAlertArea (Label alertAera) {
      alertAera_ = alertAera;
   }

   public static String appendText (String str) {
      Platform.runLater(() -> textArea_.appendText(str));
      return str;
   }

   public static String log (String str) {
      Platform.runLater(() -> textArea_.appendText(str + '\n'));
      return str;
   }

   public static String alert (String str) {
      Platform.runLater(() -> alertAera_.setText(str));
      return str;
   }

   @Override
   public void initialize (URL location, ResourceBundle resources) {
      OutputStream out = new OutputStream() {
         @Override
         public void write (int b) throws IOException {
            appendText(String.valueOf((char) b));
         }
      };
      System.setOut(new PrintStream(out, true));
   }
}
