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

   public final String name_;
   private final BlackjackSystem sys_;
   private double money_;
   private HashMap<Hand, Double> handWagerMap_;

   //---------------------------------------------------------------------------
   public BlackjackPlayer (String name) {
      name_ = name;
      sys_ = new SimpleBlackjackSystem();
      money_ = 0;
      handWagerMap_ = new HashMap<>();
   }

   /**
    * PRE-CONDITION: player has at least 'wager' amount of money.
    *
    * @param hand
    * @param wager
    */
   public void addHand (Hand hand, double wager) {
      assert (money_ >= wager);
      money_ -= wager;
      handWagerMap_.put(hand, wager);
   }

   /**
    * PRE-CONDITION: player has enough money to double down.
    *
    * @param hand
    */
   public void doubleDown (Hand hand) {
      double wager = handWagerMap_.get(hand);
      assert (money_ >= wager);
      money_ -= wager;
      handWagerMap_.put(hand, wager * 2);
   }

   /**
    * PRE-CONDITION: player has enough money to split.
    *
    * @param hand
    * @return the split HAND.
    */
   public Hand split (Hand hand) {
      double wager = handWagerMap_.get(hand);
      assert (money_ >= wager);
      money_ -= wager;
      Hand splitHand = hand.split();
      handWagerMap_.put(splitHand, wager);
      return splitHand;
   }

   /**
    * Resolves 'hand' as a bust thus removes this hand from the player.
    *
    * @param hand
    */
   public void bust (Hand hand) {
      assert (hand.getValue() > 21);
      sys_.out(BlackjackHelper.results("BUST", hand), BlackjackHelper.log());
      handWagerMap_.remove(hand);
   }

   /*
    * PRE-CONDITION: player contains no busted hands.
    * Resolves all the hands of the player.
    * @param dealerHand
    */
   public void resolveHands (Hand dealerHand) {
      int dealerHandValue = dealerHand.getValue();

      sys_.out(BlackjackHelper.hand("Dealer", dealerHand), BlackjackHelper.log());
      handWagerMap_.forEach((hand, wager) -> {
         if (hand.getValue() > dealerHandValue || dealerHandValue > 21) {
            // TODO: change these alert messages to constants
            // TODO: what happens to these messages on split hands?
            sys_.out("Nice job! You Win!", BlackjackHelper.alert());
            sys_.out(BlackjackHelper.results("WIN", hand), BlackjackHelper.log());
            money_ += wager * 2;
         }
         else if (hand.getValue() == dealerHandValue) {
            sys_.out("You tied!", BlackjackHelper.alert());
            sys_.out(BlackjackHelper.results("TIE", hand), BlackjackHelper.log());
            money_ += wager;
         }
         else {
            sys_.out("Oh dear... you lost, better luck next time :)", BlackjackHelper.alert());
            sys_.out(BlackjackHelper.results("LOSE", hand), BlackjackHelper.log());
         }
      });
   }

   //---------------------------------------------------------------------------
   public boolean hasHands () {
      return !handWagerMap_.isEmpty();
   }

   //---------------------------------------------------------------------------
   public void clearHands () {
      handWagerMap_.clear();
   }

   //---------------------------------------------------------------------------
   public double getMoney () {
      return money_;
   }

   /**
    * PRE-CONDITION: money >= 0.
    *
    * @param amount
    * @return
    */
   public BlackjackPlayer setMoney (int amount) {
      assert (amount > 0);
      money_ = amount;
      return this;
   }

}
