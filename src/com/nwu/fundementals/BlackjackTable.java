package com.nwu.fundementals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlackjackTable
{
   private static final Tool         UTIL = new Tool();
   private Deck                      deck;
   private Hand                      dealerHand;
   private int                       minWage;
   private Map<HumanPlayer, Integer> playersToBet;
   private Map<Hand, Integer>        handToWage;
   private Map<Hand, Integer>        splitsToWage;

   // TODO: Set minWage dynamically
   public BlackjackTable(int minWage)
   {
      // TODO: Generate random ID and name
      dealerHand = new Hand(new Dealer("UNKNOWN ID", "Dealer"));
      this.minWage = minWage;
      playersToBet = new HashMap<>();
      handToWage = new HashMap<>();
      splitsToWage = new HashMap<>();
   }

   public void addPlayer(HumanPlayer player)
   {
      if (player.getMoney() < minWage)
      {
         UTIL.error(player.getName() + ": Not enough money");
      }
      else
      {
         playersToBet.put(player, minWage);
      }
   }

   public void removePlayer(HumanPlayer player)
   {
      playersToBet.remove(player);
   }

   public void play()
   {
      // Initialize the round
      // -----------------------------------------------------------------------
      deck = getStandardDeck();
      playersToBet.forEach((player, wager) -> {
         if (player.getMoney() < minWage)
         {
            this.removePlayer(player);
            UTIL.gameMsg(player.toString() + " has been removed.");
         }
         else
         {
            Hand hand = new Hand(player);
            hand.add(deck.poll());
            hand.add(deck.poll());
            handToWage.put(hand, wager);
         }
      });
      dealerHand.add(deck.poll());

      // Main logic
      // -----------------------------------------------------------------------
      handToWage.forEach((hand, wage) -> {
         UTIL.display(dealerHand);
         process(hand);
      });
      process(dealerHand);

      // Resolve wages
      handToWage.forEach((hand, wage) -> resolve(hand, wage));
      splitsToWage.forEach((hand, wage) -> resolve(hand, wage));

      // Round cleanup
      // -----------------------------------------------------------------------
      handToWage.clear();
      splitsToWage.clear();
      dealerHand = new Hand(dealerHand.getOwner());
   }

   private Deck getStandardDeck()
   {
      List<Card> cards = new ArrayList<>(52);
      for (Suit suit : Suit.values())
      {
         for (int value = 1; value < 13; value++)
         {
            cards.add(new Card(suit, value));
         }
      }
      Collections.shuffle(cards);
      return new Deck(cards);
   }

   private void process(Hand hand)
   {
      boolean notDone = true;
      while (notDone)
      {
         UTIL.display(hand);
         Action action = hand.requestAction();
         UTIL.gameMsg(hand.getOwner().getName() + ": SELECTED " + action.name());
         notDone = !executeAction(action, hand);
      }
   }

   // Returns true if hand is completely done OR busted
   private boolean executeAction(Action action, Hand hand)
   {
      boolean handComplete = false;
      if (action == Action.HIT)
      {
         hand.add(deck.poll());
      }
      else if (action == Action.STAY)
      {
         handComplete = true;
      }
      else if (action == Action.DOUBLEDOWN)
      {
         // Safe cast because only HumanPlayer can doubledown
         HumanPlayer player = (HumanPlayer) hand.getOwner();
         int wage = handToWage.get(hand);
         if (player.getMoney() >= wage)
         {
            handToWage.replace(hand, wage + player.pay(wage));
            hand.add(deck.poll());
            handComplete = true;
         }
         else
         {
            // TODO: ERROR HANDLE
            UTIL.error("Can't DD, not enough money");
         }
      }
      else if (action == Action.SPLIT)
      {
         // Safe cast because only HumanPlayer can split
         HumanPlayer player = (HumanPlayer) hand.getOwner();
         int wage = handToWage.get(hand);
         if (player.getMoney() >= wage)
         {
            Hand other = hand.split();
            other.add(deck.poll());
            hand.add(deck.poll());
            splitsToWage.put(other, player.pay(wage));
            process(other);
         }
         else
         {
            // TODO: ERROR HANDLE
            UTIL.error("ERROR: Can't SPLIT, not enough money");
         }
      }
      else if (action == Action.UNKNOWN)
      {
         // TODO: ERROR HANDLE
         UTIL.error("UNKNOWN ACTION...");
      }
      if (hand.busted())
      {
         UTIL.gameMsg(hand.getOwner().getName() + ": BUSTED with " + hand.asString() + " (" + hand.getValue() + ")");
      }
      return handComplete || hand.busted();
   }

   private void resolve(Hand hand, int wage)
   {
      HumanPlayer player = (HumanPlayer) hand.getOwner();
      String message = player.getName() + ": ";
      if (hand.busted())
      {
         // lost
         message += "LOST with ";
      }
      else if (dealerHand.busted() || hand.getValue() > dealerHand.getValue())
      {
         // won
         player.setMoney(player.getMoney() + wage * 2);
         message += "WON with ";
      }
      else if (hand.getValue() == dealerHand.getValue())
      {
         // push
         message += "TIED with ";
      }
      else
      {
         // lost
         message += "LOST with ";
      }
      message += hand.asString();
      message += " (";
      message += hand.busted() ? "BUSTED" : hand.getValue();
      message += ")";
      UTIL.gameMsg(message);
   }
}
