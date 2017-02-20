package com.nwu.backend;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.nwu.backend.BlackjackPlayer.Action;

public class BlackjackGame {
   // TODO: Think of a better way to expose actionsAvailable_
   //       Might need to modify play(), actionPhase()
   //---------------------------------------------------------------------------
   private final int kDEALER_THINK_TIME_MS = 1000;
   private final BlackjackSystem sys_;
   private final BlackjackHelper helper_;

   private double minWager_;
   private Deck deck_;
   private Hand dealerHand_;
   private BlackjackPlayer player_;

   private volatile boolean selectedAction_;
   private volatile boolean selectedBet_;

   private boolean gameOverFlag_;
   private Set<Action> actionsAvailable_;
   private boolean betAvailable_;
   private Action action_;
   private double bet_;

   //---------------------------------------------------------------------------
   public BlackjackGame(BlackjackPlayer player, double minWager) {
      sys_ = new SimpleBlackjackSystem();
      helper_ = new BlackjackHelper();

      minWager_ = minWager;
      player_ = player;
      dealerHand_ = new Hand();

      gameOverFlag_ = false;
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
      gameOverFlag_ = false;
      deck_ = Deck.standard52Deck();
      actionsAvailable_.clear();

      player_.clearHands();
      dealerHand_.clear();
      dealerHand_.add(deck_.drawTop());
      Hand playerHand = new Hand().add(deck_.drawTop())
                                  .add(deck_.drawTop());

      // Game over
      if (player_.getMoney() < minWager_) {
         sys_.out(helper_.gameOver(player_));
         betAvailable_ = false;
         actionsAvailable_.clear();
         gameOverFlag_ = true;
         return;
      }

      while (!getAppropiateBetAmount()) ;

      // player will have enough money to place 'bet_' amount
      player_.addHand(playerHand, bet_);
      actionsAvailable_.addAll(Arrays.asList(Action.HIT, Action.STAY, Action.DOUBLEDOWN));
      // actionPhase() will add split if the hand is splittable
      // and remove doubledown if player doesn't have enough money
      actionPhase(playerHand);

      // players bust immediately in action phase, checking for non-bust hands
      if (player_.hasHands()) {
         player_.resolveHands(dealerAI());
      }
      sys_.out("----------------------------------------------");
   }

   //---------------------------------------------------------------------------
   public void setAction(Action action) {
      action_ = action;
      selectedAction_ = true;
   }

   //---------------------------------------------------------------------------
   public void setBet(double bet) {
      bet_ = bet;
      selectedBet_ = true;
   }

   //---------------------------------------------------------------------------
   public double getMinWager() {
      return minWager_;
   }

   public boolean gameOver() {
      return gameOverFlag_;
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
    * Sets up bet_ to be an amount payable by the player.
    *
    * @return true if they player will have enough money to pay 'bet_' amount
    */
   private boolean getAppropiateBetAmount() {
      betAvailable_ = true;
      // TODO: fix busy wait?
      while (!selectedBet_) ;
      selectedBet_ = betAvailable_ = false;

      if (bet_ < minWager_) {
         sys_.out(helper_.errBetLessThanMinWager(player_, minWager_));
         return false;
      }
      else if (bet_ > player_.getMoney()) {
         sys_.out(helper_.errNoMoney(player_));
         return false;
      }

      return true;
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

      if (player_.getMoney() < bet_) {
         actionsAvailable_.remove(Action.DOUBLEDOWN);
      }

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
            // if we got here, the player should have enough money to double down
            actionsAvailable_.clear();
            sys_.out("DOUBLEDOWN");
            player_.doubleDown(hand);
            if (hand.add(deck_.drawTop()).getValue() > 21) {
               player_.bust(hand);
            }
            return;

         case SPLIT:
            // if we got here, the player should have enough money to split
            actionsAvailable_.remove(Action.SPLIT);
            sys_.out("SPLIT");
            Hand splitHand = player_.split(hand);
            actionPhase(splitHand);
            actionsAvailable_.add(Action.HIT);
            actionsAvailable_.add(Action.STAY);
            break;
      }
      actionPhase(hand);
   }

}
