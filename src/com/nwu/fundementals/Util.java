package com.nwu.fundementals;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Util
{
   // ------------------------------------------------------------------
   // Sys
   public static class Sys
   {
      /**
       * Decorates message with square brackets
       * 
       * @param message
       */
      public static void decorateSB(String message)
      {
         System.out.println("[" + message + "]");
      }

      public static void error(String message)
      {
         decorateSB("ERROR: " + message);
      }

      public static void displayChoices(Hand hand)
      {
         String selectChoices = "Select an Action: HIT, STAY";
         if (hand.size() == 2)
         {
            selectChoices += ", DD";
         }
         if (hand.splitable())
         {
            selectChoices += ", SPLIT";
         }
         System.out.println(selectChoices);
      }

      public static void display(Hand hand)
      {
         String message = hand.getOwner().getName() + ": " + hand.asString() + " (";
         message += hand.busted() ? "BUSTED" : hand.getValue();
         message += ")";
         System.out.println(message);
      }

      public static void displayMoney(HumanPlayer player)
      {
         decorateSB(player.getName() + ": " + "has $" + player.getMoney());
      }
   } // Sys

   // ------------------------------------------------------------------
   // Input
   public static class Input
   {
      public static int promptWage(int minWager)
      {
         System.out.println("Enter your wager amount (minimum " + minWager + "):");
         Scanner scan = new Scanner(System.in);
         while (!scan.hasNextLine());
         while (true)
         {
            try
            {
               int input = scan.nextInt();
               if (input < 0)
               {
                  Sys.error("Please enter a positive number");
               }
               else if (input < minWager)
               {
                  Sys.error("The minimum wager is " + minWager);
               }
               else
               {
                  return input;
               }
            }
            catch (InputMismatchException ex)
            {
               Sys.error("Enter an Integer");
            }
         }
      }

      public static Action promptAction(Hand hand)
      {
         Scanner scan = new Scanner(System.in);
         while (!scan.hasNext());
         String input = scan.next().toUpperCase();
         if (input.equals("HIT"))
         {
            return Action.HIT;
         }
         else if (input.equals("STAY"))
         {
            return Action.STAY;
         }
         else if (input.equals("DD") && hand.size() == 2)
         {
            return Action.DOUBLEDOWN;
         }
         else if (input.equals("SPLIT") && hand.splitable())
         {
            return Action.SPLIT;
         }
         else
         {
            return Action.UNKNOWN;
         }
      }
   } // Input

   // ------------------------------------------------------------------
   // Tool
   public static class Tool
   {
      public enum Result
      {
         WIN,
         TIE,
         LOSE
      }

      // Assumes out is empty and modifies it
      public static Result buildResultString(Hand dealerHand, Hand playerHand, StringBuilder out)
      {
         Result res = null;
         out.append(playerHand.getOwner().getName() + ": ");
         if (playerHand.busted())
         {
            out.append("LOST with ");
            res = Result.LOSE;
         }
         else if (dealerHand.busted() || playerHand.getValue() > dealerHand.getValue())
         {
            out.append("WON with ");
            res = Result.WIN;
         }
         else if (playerHand.getValue() == dealerHand.getValue())
         {
            out.append("TIED with ");
            res = Result.TIE;
         }
         else
         {
            out.append("LOST with ");
            res = Result.LOSE;
         }
         out.append(playerHand.asString());
         out.append(" (");
         out.append(playerHand.busted() ? "BUSTED" : playerHand.getValue());
         out.append(")");
         return res;
      }
      

      /**
       * Selects an action.
       * 
       * @param hand
       * @return Action specified to take taken by the hand.
       */
      public static Action requestAction(Hand hand)
      {
         Player.Type playerT = hand.getOwner().getType();
         if (playerT == Player.Type.HUMAN)
         {
            Util.Sys.displayChoices(hand);
            return Util.Input.promptAction(hand);
         }
         else if (playerT == Player.Type.DEALER)
         {
            try
            {
               Thread.sleep(Dealer.THINK_TIME_MS);
            }
            catch (InterruptedException e)
            {
               e.printStackTrace();
            }
            
            if (hand.getValue() < 17)
            {
               return Action.HIT;
            }
            else
            {
               return Action.STAY;
            }
         }
         else 
         {
            //Shouldn't get here
            return Action.UNKNOWN;
         }
      }
   } // Tool
}
