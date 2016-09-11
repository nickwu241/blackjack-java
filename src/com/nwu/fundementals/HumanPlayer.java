package com.nwu.fundementals;

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
      String selectChoices = "Select an Action: HIT, STAY";
      if (hand.size() == 2)
      {
         selectChoices += ", DD";
      }
      if (hand.splitable())
      {
         selectChoices += ", SPLIT";
      }
      System.out.println(selectChoices);
      Scanner scan = new Scanner(System.in);
      while (!scan.hasNext());
      String input = scan.next().toUpperCase();
      if (input.equals("HIT"))
      {
         return Action.HIT;
      }
      else if (input.equals("STAY"))
      {
         return Action.STAY;
      }
      else if (input.equals("DD") && hand.size() == 2)
      {
         return Action.DOUBLEDOWN;
      }
      else if (input.equals("SPLIT") && hand.splitable())
      {
         return Action.SPLIT;
      }
      else
      {
         return Action.UNKNOWN;
      }
   }
}