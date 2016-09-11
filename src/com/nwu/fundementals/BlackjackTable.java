package com.nwu.fundementals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlackjackTable
{
   private static final Tool      UTIL  = new Tool();
   private static final UserInput INPUT = new UserInput();
   private Deck                   deck;
   private Hand                   dealerHand;
   private int                    minWager;
   private List<HumanPlayer>      players;
   private List<Hand>             firstHands;
   private List<Hand>             splitHands;

   // TODO: Set minWager dynamically
   public BlackjackTable(int minWage)
   {
      // TODO: Generate random ID and name
      dealerHand = new Hand(new Dealer("UNKNOWN ID", "Dealer"));
      this.minWager = minWage;
      players = new ArrayList<>();
      firstHands = new ArrayList<>();
      splitHands = new ArrayList<>();
   }

   public void addPlayer(HumanPlayer player)
   {
      if (player.getMoney() < minWager)
      {
         UTIL.error(player.getName() + ": Not enough money");
      }
      else
      {
         players.add(player);
      }
   }

   public void removePlayer(HumanPlayer player)
   {
      players.remove(player);
   }

   public void play()
   {
      // Initialize the round
      // -----------------------------------------------------------------------
      deck = getStandardDeck();
      players.forEach((player) -> {
         if (player.getMoney() < minWager)
         {
            this.removePlayer(player);
            UTIL.gameMsg(player.toString() + " has been removed.");
         }
         else
         {
            Hand hand = new Hand(player);
            hand.add(deck.poll());
            hand.add(deck.poll());
            hand.setWager(player.pay(INPUT.promptWage(minWager)));
            firstHands.add(hand);
         }
      });
      dealerHand.add(deck.poll());

      // Main logic
      // -----------------------------------------------------------------------
      firstHands.forEach((hand) -> {
         UTIL.display(dealerHand);
         process(hand);
      });
      process(dealerHand);

      // Resolve wages
      firstHands.forEach((hand) -> resolve(hand));
      splitHands.forEach((hand) -> resolve(hand));

      // Round cleanup
      // -----------------------------------------------------------------------
      firstHands.clear();
      splitHands.clear();
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
         int wager = hand.getWager();
         if (player.getMoney() >= wager)
         {
            hand.setWager(wager + player.pay(wager));
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
         int wager = hand.getWager();
         if (player.getMoney() >= wager)
         {
            Hand other = hand.split();
            other.add(deck.poll());
            hand.add(deck.poll());
            other.setWager(player.pay(wager));
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

   private void resolve(Hand hand)
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
         player.setMoney(player.getMoney() + hand.getWager() * 2);
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
      UTIL.displayMoney(player);
   }

}
