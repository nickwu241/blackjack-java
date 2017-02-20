package com.nwu.backend;

public class BlackjackHelper {
   //---------------------------------------------------------------------------
   public static String hand(String person, Hand hand) {
      return person + ": " + hand.string() + " (" + hand.getValue() + ")";
   }

   //---------------------------------------------------------------------------
   public static String results(String result, Hand hand) {
      switch (result) {
         case "BUST":
            return "You busted with " + hand.string() + " (" + hand.getValue() + ")";
         case "WIN":
            return "You won with " + hand.string() + " (" + hand.getValue() + ")";
         case "TIE":
            return "You tied with " + hand.string() + " (" + hand.getValue() + ")";
         case "LOSE":
            return "You lost with " + hand.string() + " (" + hand.getValue() + ")";
         default:
            throw new RuntimeException("UNEXPECTED RESULT STRING");
      }
   }
}
