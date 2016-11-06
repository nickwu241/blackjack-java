package com.nwu.fundementals;

public class HumanPlayer extends Player
{
   private int money;

   public HumanPlayer(String id, String name)
   {
      super(id, name);
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
   public Type getType()
   {
      return Player.Type.HUMAN;
   }
}
