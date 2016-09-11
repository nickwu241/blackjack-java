package com.nwu.fundementals;

public class Tool
{
   public void gameMsg(String message)
   {
      System.out.println("[" + message + "]");
   }

   public void error(String message)
   {
      gameMsg("ERROR: " + message);
   }

   public void display(Hand hand)
   {
      String message = hand.getOwner().getName() + ": " + hand.asString() + " (";
      message += hand.busted() ? "BUSTED" : hand.getValue();
      message += ")";
      System.out.println(message);
   }

   public void displayMoney(HumanPlayer player)
   {
      gameMsg(player.getName() + ": " + "has $" + player.getMoney());
   }
}
