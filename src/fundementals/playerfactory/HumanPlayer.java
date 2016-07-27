package fundementals.playerfactory;

import java.util.ArrayList;
import java.util.List;

import fundementals.Hand;

public class HumanPlayer extends BasicPlayer
{
   private List<Hand> hands;
   private int        money;
   private int        bet;

   HumanPlayer(String id, String name)
   {
      this.id = id;
      this.name = name;
      this.hands = new ArrayList<Hand>();
      this.money = 0;
      this.bet = 0;
   }

   public List<Hand> getHands()
   {
      return hands;
   }

   public int getMoney()
   {
      return money;
   }

   public void setMoney(int value)
   {
      money = value;
   }

   public int getBet()
   {
      return bet;
   }

   public void setBet(int value)
   {
      bet = value;
   }

   @Override
   public String getPlayerType()
   {
      return "human";
   }

}
