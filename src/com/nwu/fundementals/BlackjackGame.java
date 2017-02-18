package com.nwu.fundementals;

import static com.nwu.fundementals.BlackjackPlayer.Action;

public class BlackjackGame {
   private final int kDEALER_THINK_TIME_MS = 1000;

   private int minWager_;

   private Deck deck_;
   private Hand dealerHand_;
   private BlackjackPlayer player_;

   private volatile Action selectedAction_;

   public BlackjackGame(BlackjackPlayer player, int minWager) {
      minWager_ = minWager;
      player_ = player;
      dealerHand_ = new Hand();
      selectedAction_ = Action.NONE;
   }

   public void play() {
      deck_ = Deck.standard52Deck();

      player_.clearHands();
      dealerHand_.clear();
      dealerHand_.add(deck_.drawTop());

      Hand playerHand = new Hand().add(deck_.drawTop())
                                  .add(deck_.drawTop());
      if (player_.addHand(playerHand, minWager_)) {
         actionPhase(playerHand);
         player_.resolveHands(dealerAI());
      }
      else {
         // TODO: no money
      }
   }

   /**
    * Logic for a dealer at a casino.
    *
    * @return the final value of the dealer's hand.
    */
   private int dealerAI() {
      while (dealerHand_.getValue() < 17) {
         dealerHand_.add(deck_.drawTop());
         BlackjackSystem.out("Dealer has: " + dealerHand_.string());
         try {
            Thread.sleep(kDEALER_THINK_TIME_MS);
         }
         catch (InterruptedException e) {
            e.printStackTrace();
         }
      }

      return dealerHand_.getValue();
   }

   /**
    * Finishes a player's actions.
    *
    * @param hand
    */
   private void actionPhase(Hand hand) {
      BlackjackSystem.out("Dealer has: " + dealerHand_.string());
      BlackjackSystem.out("You have: " + hand.string() + " (" + hand.getValue() + ")");
      // TODO: don't not busy wait
      while (selectedAction_ == Action.NONE) ;

      Action action = selectedAction_;
      selectedAction_ = Action.NONE;

      switch (action) {

         case HIT:
            BlackjackSystem.out("HIT");
            if (hand.add(deck_.drawTop()).getValue() > 21) {
               player_.bust(hand);
               return;
            }
            break;

         case STAY:
            BlackjackSystem.out("STAY");
            return;

         case DOUBLEDOWN:
            BlackjackSystem.out("DOUBLEDOWN");
            if (player_.doubleDown(hand)) {
               if (hand.add(deck_.drawTop()).getValue() > 21) {
                  player_.bust(hand);
                  return;
               }
               return;
            }
            else {
               // TODO: no money
               return;
            }

         case SPLIT:
            BlackjackSystem.out("SPLIT");
            Hand splitHand = player_.split(hand);
            if (splitHand == null) {
               // TODO: no money
               return;
            }
            actionPhase(splitHand);
            break;
      }
      actionPhase(hand);
   }

   public void setAction(Action action) {
      selectedAction_ = action;
   }
}
