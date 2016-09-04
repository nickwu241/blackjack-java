package fundementals.blackjacktable;

import fundementals.Hand;

public class Tool
{

   //TODO: Better messages?
   
   public void error(String message){
      gameMsg("ERROR: " + message);
   }
   
   public void gameMsg(String message)
   {
      System.out.println("[" + message + "]");
   }

   public void display(Hand hand)
   {
      String message = hand.getOwner().getName() + ": " + hand.asString() + " (";
      message += hand.busted() ? "BUSTED" : hand.getValue();
      message += ")";
      System.out.println(message);
   }
}
