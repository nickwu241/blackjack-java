import java.util.Scanner;

import fundementals.BlackjackUtil;
import fundementals.blackjacktable.BlackjackTable;
import fundementals.playerfactory.HumanPlayer;
import fundementals.playerfactory.PlayerFactory;

public class Blackjack
{
   public static void main(String args[])
   {
      
      BlackjackTable table1 = new BlackjackTable();
      PlayerFactory playerFactory = BlackjackUtil.getInstance().getPlayerFactory();
      HumanPlayer Nick = (HumanPlayer)playerFactory.makePlayer("H", "54321", "Nick");
      table1.addPlayer(Nick);
      Nick.setMoney(500);
      table1.playRound();
      
      /*
      Scanner scanner = new Scanner(System.in);
      while (scanner.hasNextLine()){
         String in = scanner.nextLine();
         System.out.println("Input = " + in);
      }
      */
   }
}