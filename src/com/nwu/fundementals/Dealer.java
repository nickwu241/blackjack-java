package com.nwu.fundementals;

public class Dealer extends Player {
   public final static int THINK_TIME_MS = 1000;

   public Dealer(String id, String name) {
      super(id, name);
   }

   @Override
   public Type getType() {
      return Player.Type.DEALER;
   }
}
