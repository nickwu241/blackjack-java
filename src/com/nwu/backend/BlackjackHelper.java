package com.nwu.backend;

import com.nwu.client.view.Activity;

import java.util.function.Function;

public abstract class BlackjackHelper {
   private static final String kHAND_FORMAT = "%s: %s (%d)";
   private static final String kRESULTS_FORMAT = "You %s with %s (%d)";
   private static final String kGAMEOVER_FORMAT = "GAMEOVER: The casino got you good :) You don't have enough money to play!";

   private static final String kERR_NOMONEY_FORMAT = "Nice try! You only have $%.2f";
   private static final String kERR_LESS_THAN_MINWAGER_FORMAT = "You're too cheap! Need to BET at least $%.2f";

   //---------------------------------------------------------------------------
   public static Function<String, String> log() {
      return Activity::log;
   }

   //---------------------------------------------------------------------------
   public static Function<String, String> alert() {
      return Activity::alert;
   }

   //---------------------------------------------------------------------------
   public static String hand (String person, Hand hand) {
      return String.format(kHAND_FORMAT, person, hand.string(), hand.getValue());
   }

   //---------------------------------------------------------------------------
   public static String results (String result, Hand hand) {
      switch (result) {
         case "BUST":
            return String.format(kRESULTS_FORMAT, "busted", hand.string(), hand.getValue());
         case "WIN":
            return String.format(kRESULTS_FORMAT, "won", hand.string(), hand.getValue());
         case "TIE":
            return String.format(kRESULTS_FORMAT, "tied", hand.string(), hand.getValue());
         case "LOSE":
            return String.format(kRESULTS_FORMAT, "lost", hand.string(), hand.getValue());
         default:
            // TODO: probably better to not throw runtime exception
            throw new RuntimeException("UNEXPECTED RESULT STRING");
      }
   }

   //---------------------------------------------------------------------------
   public static String gameOver (BlackjackPlayer player) {
      // TODO: Add player names to string format?
      return String.format(kGAMEOVER_FORMAT);
   }

   //---------------------------------------------------------------------------
   public static String errNoMoney (BlackjackPlayer player) {
      // TODO: Add player names to string format?
      return String.format(kERR_NOMONEY_FORMAT, player.getMoney());
   }

   //---------------------------------------------------------------------------
   public static String errBetLessThanMinWager (BlackjackPlayer player, double minWager) {
      // TODO: Add player names to string format?
      return String.format(kERR_LESS_THAN_MINWAGER_FORMAT, minWager);
   }
}
