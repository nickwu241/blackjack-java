package fundementals;

import java.util.Scanner;

public class HumanPlayer implements Player
{
   private final String id;
   private String       name;
   private int          money;

   public HumanPlayer(String id, String name)
   {
      this.id = id;
      this.name = name;
   }

   public void setMoney(int amount)
   {
      this.money = amount;
   }

   public int getMoney()
   {
      return money;
   }

   public int pay(int amount)
   {
      int tmp = money - amount;
      if (tmp < 0)
      {
         // TODO: Error handle
         System.out.println("ERROR: can't pay this amount");
         return 0;
      }
      else
      {
         return this.money = tmp;
      }
   }

   @Override
   public String getID()
   {
      return id;
   }

   @Override
   public String getName()
   {
      return name;
   }

   @Override
   public Action requestAction(Hand hand)
   {
      System.out.println("Select an Action: HIT, STAY, DD, SPLIT");
      Scanner scanner = new Scanner(System.in);
      while (!scanner.hasNext());
      String input = scanner.next();
      if (input.equals("HIT"))
      {
         return Action.HIT;
      }
      else if (input.equals("STAY"))
      {
         return Action.STAY;
      }
      else if (input.equals("DD"))
      {
         return Action.DOUBLEDOWN;
      }
      else if (input.equals("SPLIT"))
      {
         return Action.SPLIT;
      }
      else
      {
         return Action.UNKNOWN;
      }
   }
}
