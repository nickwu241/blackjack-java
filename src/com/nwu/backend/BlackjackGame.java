package com.nwu.backend;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.nwu.backend.BlackjackPlayer.Action;

public class BlackjackGame {
   //---------------------------------------------------------------------------
   private final int kDEALER_THINK_TIME_MS = 1000;
   private final BlackjackSystem sys_;
   private final BlackjackHelper helper_;

   private int minWager_;
   private Deck deck_;
   private Hand dealerHand_;
   private BlackjackPlayer player_;

   private volatile boolean selectedAction_;
   private volatile boolean selectedBet_;

   private Set<Action> actionsAvailable_;
   private boolean betAvailable_;
   private Action action_;
   private int bet_;

   //---------------------------------------------------------------------------
   public BlackjackGame(BlackjackPlayer player, int minWager) {
      sys_ = new BlackjackSystem();
      helper_ = new BlackjackHelper();

      minWager_ = minWager;
      player_ = player;
      dealerHand_ = new Hand();

      selectedAction_ = selectedBet_ = false;
      actionsAvailable_ = new HashSet<>();
      betAvailable_ = false;

      action_ = Action.NONE;
      bet_ = 10;
   }

   /**
    * Plays one round of blackjack.
    */
   public void play() {
      deck_ = Deck.standard52Deck();
      actionsAvailable_.clear();

      player_.clearHands();
      dealerHand_.clear();
      dealerHand_.add(deck_.drawTop());
      Hand playerHand = new Hand().add(deck_.drawTop())
                                  .add(deck_.drawTop());

      betAvailable_ = true;
      // TODO: fix busy wait
      while (!selectedBet_) ;
      selectedBet_ = betAvailable_ = false;

      // if player has 'bet_' enough money
      if (player_.addHand(playerHand, bet_)) {
         actionsAvailable_.addAll(Arrays.asList(Action.HIT, Action.STAY, Action.DOUBLEDOWN));
         // actionPhase() will add split if the hand is splittable
         actionPhase(playerHand);

         // players bust immediately in action phase, checking for non-bust hands
         if (player_.hasHands()) {
            player_.resolveHands(dealerAI());
         }
      }
      else {
         // TODO: no money
      }

      BlackjackSystem.out("----------------------------------------------");
   }

   //---------------------------------------------------------------------------
   public void setAction(Action action) {
      action_ = action;
      selectedAction_ = true;
   }

   //---------------------------------------------------------------------------
   public void setBet(int bet) {
      bet_ = bet;
      selectedBet_ = true;
   }

   //---------------------------------------------------------------------------
   public int getMinWager() {
      return minWager_;
   }

   //---------------------------------------------------------------------------
   public Set<Action> getActionsAvailable() {
      return Collections.unmodifiableSet(actionsAvailable_);
   }

   //---------------------------------------------------------------------------
   public boolean getBetAvailable() {
      return betAvailable_;
   }

   /**
    * Logic for a dealer at a casino.
    *
    * @return the dealer's hand.
    */
   private Hand dealerAI() {
      while (dealerHand_.getValue() < 17) {
         dealerHand_.add(deck_.drawTop());
         sys_.out(helper_.hand("Dealer", dealerHand_));
         try {
            Thread.sleep(kDEALER_THINK_TIME_MS);
         }
         catch (InterruptedException e) {
            e.printStackTrace();
         }
      }

      return dealerHand_;
   }

   /**
    * Finishes a player's actions.
    *
    * @param hand
    */
   private void actionPhase(Hand hand) {
      sys_.out(helper_.hand("Dealer", dealerHand_));
      sys_.out(helper_.hand("You", hand));

      if (hand.splittable()) {
         actionsAvailable_.add(Action.SPLIT);
      }

      // TODO: fix busy wait?
      while (!selectedAction_) ;
      selectedAction_ = false;

      switch (action_) {
         case HIT:
            actionsAvailable_.remove(Action.DOUBLEDOWN);
            actionsAvailable_.remove(Action.SPLIT);
            sys_.out("HIT");
            if (hand.add(deck_.drawTop()).getValue() > 21) {
               player_.bust(hand);
               return;
            }
            break;

         case STAY:
            actionsAvailable_.clear();
            sys_.out("STAY");
            return;

         case DOUBLEDOWN:
            actionsAvailable_.clear();
            sys_.out("DOUBLEDOWN");
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
            actionsAvailable_.remove(Action.SPLIT);
            sys_.out("SPLIT");
            Hand splitHand = player_.split(hand);
            if (splitHand == null) {
               // TODO: no money
               return;
            }
            actionPhase(splitHand);
            actionsAvailable_.add(Action.HIT);
            actionsAvailable_.add(Action.STAY);
            break;
      }
      actionPhase(hand);
   }

}
