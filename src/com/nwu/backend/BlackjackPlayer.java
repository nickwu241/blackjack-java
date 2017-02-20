package com.nwu.backend;

import java.util.HashMap;

public class BlackjackPlayer {
   //---------------------------------------------------------------------------
   public enum Action {
      HIT,
      STAY,
      DOUBLEDOWN,
      SPLIT,
      NONE
   }

   final public String name_;
   private int money_;
   private HashMap<Hand, Integer> handWagerMap_;

   //---------------------------------------------------------------------------
   public BlackjackPlayer(String name) {
      name_ = name;
      money_ = 0;
      handWagerMap_ = new HashMap<>();
   }

   /**
    * @param hand
    * @param wager
    * @return false if the player doesn't have 'wager' money.
    */
   public boolean addHand(Hand hand, int wager) {
      if (money_ - wager < 0) {
         return false;
      }
      else {
         money_ -= wager;
         handWagerMap_.put(hand, wager);
         return true;
      }
   }

   /**
    * @param hand
    * @return false if the player doesn't have enough money.
    */
   public boolean doubleDown(Hand hand) {
      int wager = handWagerMap_.get(hand);
      if (money_ - wager < 0) {
         return false;
      }
      else {
         money_ -= wager;
         handWagerMap_.put(hand, wager * 2);
         return true;
      }
   }

   /**
    * @param hand
    * @return The split HAND OR null if the player doesn't have enough money.
    */
   public Hand split(Hand hand) {
      int wager = handWagerMap_.get(hand);
      if (money_ - wager < 0) {
         return null;
      }
      else {
         money_ -= wager;
         Hand splitHand = hand.split();
         handWagerMap_.put(splitHand, wager);
         return splitHand;
      }
   }

   /**
    * Resolves 'hand' as a bust thus removes this hand from the player.
    *
    * @param hand
    */
   public void bust(Hand hand) {
      BlackjackSystem.out(BlackjackHelper.results("BUST", hand));
      handWagerMap_.remove(hand);
   }

   /*
    * PRE-CONDITION: player contains no busted hands
    * Resolves all the hands of the player.
    * @param dealerHand
    */
   public void resolveHands(Hand dealerHand) {
      int dealerHandValue = dealerHand.getValue();

      BlackjackSystem.out(BlackjackHelper.hand("Dealer", dealerHand));
      handWagerMap_.forEach((hand, wager) -> {
         if (hand.getValue() > dealerHandValue || dealerHandValue > 21) {
            BlackjackSystem.out(BlackjackHelper.results("WIN", hand));
            money_ += wager * 2;
         }
         else if (hand.getValue() == dealerHandValue) {
            BlackjackSystem.out(BlackjackHelper.results("TIE", hand));
            money_ += wager;
         }
         else {
            BlackjackSystem.out(BlackjackHelper.results("LOSE", hand));
         }
      });
   }

   //---------------------------------------------------------------------------
   public boolean hasHands() {
      return !handWagerMap_.isEmpty();
   }

   //---------------------------------------------------------------------------
   public void clearHands() {
      handWagerMap_.clear();
   }

   //---------------------------------------------------------------------------
   public int getMoney() {
      return money_;
   }

   //---------------------------------------------------------------------------
   public BlackjackPlayer setMoney(int money) {
      money_ = money;
      return this;
   }

}
