package com.nwu.fundementals;

import java.util.HashMap;

public class BlackjackPlayer {
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

   public void clearHands() {
      handWagerMap_.clear();
   }

   public void bust(Hand hand) {
      money_ -= handWagerMap_.remove(hand);
   }

   /*
    * PRE-CONDITION: player contains no busted hands.
    * @param dealerHandValue
    */
   public void resolveHands(int dealerHandValue) {
      BlackjackSystem.out("Dealer has: " + dealerHandValue);
      handWagerMap_.forEach((hand, wager) -> {
         if (hand.getValue() > dealerHandValue || dealerHandValue > 21) {
            BlackjackSystem.out("You won with " + hand.string() + " (" + hand.getValue() + ")");
            money_ += wager * 2;
         }
         else if (hand.getValue() == dealerHandValue) {
            BlackjackSystem.out("You tied with " + hand.string() + " (" + hand.getValue() + ")");
            money_ += wager;
         }
         else {
            BlackjackSystem.out("You lost with " + hand.string() + " (" + hand.getValue() + ")");
            money_ -= wager;
         }
      });
   }

   public int getMoney() {
      return money_;
   }

   public BlackjackPlayer setMoney(int money) {
      money_ = money;
      return this;
   }
}
