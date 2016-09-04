package client;

import fundementals.HumanPlayer;
import fundementals.blackjacktable.BlackjackTable;

public class Main
{
   public static void main(String args[])
   {
      BlackjackTable table = new BlackjackTable(10);
      HumanPlayer nick = new HumanPlayer("ID", "Nick");
      nick.setMoney(100);
      table.addPlayer(nick);
      table.play();
   }
}
