package com.nwu.fundementals;

public class GenericPlayer extends Player {
   private int money_;

   public GenericPlayer(String id, String name) {
      super(id, name);
   }

   public int getMoney() {
      return money_;
   }

   public void setMoney(int amount) {
      if (amount < 0) {
         throw new IllegalArgumentException("Amount (" + amount + ") has to be positive");
      }
      money_ = amount;
   }

   @Override
   public Type getType() {
      return Type.HUMAN;
   }
}
