package com.nwu.fundementals;

public class HumanPlayer implements Player
{
   private final String           id;
   private String                 name;
   private int                    money;

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
      int finalMoney = money - amount;
      if (finalMoney < 0)
      {
         // TODO: Error handle
         System.out.println("ERROR: can't pay this amount");
         finalMoney = 0;
      }
      else
      {
         this.money = finalMoney;
      }
      return finalMoney;
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
      Util.Sys.displayChoices(hand);
      return Util.Input.promptAction(hand);
   }
}
