package com.nwu.client;

import java.util.Scanner;

import com.nwu.fundementals.BlackjackTable;
import com.nwu.fundementals.HumanPlayer;

public class Main
{
   public static void main(String args[])
   {
      BlackjackTable table = new BlackjackTable(10);
      Scanner in = new Scanner(System.in);
      System.out.println("Enter you name: ");
      String input = "";
      while (!in.hasNextLine());
      input = in.nextLine();

      HumanPlayer player = new HumanPlayer("ID", input);
      player.setMoney(1000);
      table.addPlayer(player);
      boolean playAgain = true;
      while (playAgain)
      {
         table.play();
         System.out.println("Would you like to play again? (YES/NO)");
         input = "";
         while (!(input.equals("YES") || input.equals("NO")))
         {
            if (in.hasNextLine()) input = in.nextLine();
         }
         if (input.equals("NO"))
         {
            break;
         }
      }

   }
}
