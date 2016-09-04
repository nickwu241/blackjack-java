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
      System.out.println(hand.getOwner().getName() + ": " + hand.asString() + " (" + hand.getValue() + ")");
   }
}
