package com.nwu.fundementals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlackjackTable
{
   private Deck              deck;
   private Hand              dealerHand;
   private int               minWager;
   private List<HumanPlayer> players;
   private List<Hand>        firstHands;
   private List<Hand>        splitHands;

   public BlackjackTable(int minWage)
   {
      // TODO: Generate random ID and name
      dealerHand = new Hand(new Dealer("UNKNOWN ID", "Dealer"));
      this.minWager = minWage;
      players = new ArrayList<>();
      firstHands = new ArrayList<>();
      splitHands = new ArrayList<>();
   }

   public void setMinWager(int minWage)
   {
      this.minWager = minWage;
   }

   public void addPlayer(HumanPlayer player)
   {
      if (player.getMoney() < minWager)
      {
         Util.Sys.error(player.getName() + ": Not enough money");
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

   public void init()
   {
      // -----------------------------------------------------------------------
      // Round cleanup
      firstHands.clear();
      splitHands.clear();
      dealerHand = new Hand(dealerHand.getOwner());

      // -----------------------------------------------------------------------
      // Setup the round
      deck = getStandardDeck();
      List<HumanPlayer> removePlayers = new ArrayList<>();
      players.forEach((player) -> {
         if (player.getMoney() < minWager)
         {
            removePlayers.add(player);
         }
         else
         {
            Hand hand = new Hand(player);
            hand.add(deck.poll());
            hand.add(deck.poll());
            hand.setWager(player.pay(Util.Input.promptWage(minWager)));
            firstHands.add(hand);
         }
      });
      removePlayers.forEach(player -> {
         removePlayer(player);
         Util.Sys.decorateSB(player.getName() + " has been removed.");
      });
      dealerHand.add(deck.poll());
   }

   public void play()
   {
      init();
      // -----------------------------------------------------------------------
      // Main logic
      firstHands.forEach((hand) -> {
         Util.Sys.display(dealerHand);
         process(hand);
      });
      process(dealerHand);

      // Resolve wages
      firstHands.forEach((hand) -> resolve(hand));
      splitHands.forEach((hand) -> resolve(hand));
   }

   private Deck getStandardDeck()
   {
      List<Card> cards = new ArrayList<>(52);
      for (Card.Suit suit : Card.Suit.values())
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
         Util.Sys.display(hand);
         Action action = Util.Tool.requestAction(hand);
         Util.Sys.decorateSB(hand.getOwner().getName() + ": SELECTED " + action.name());
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
            Util.Sys.error("Can't DD, not enough money");
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
            Util.Sys.error("Can't SPLIT, not enough money");
         }
      }
      else if (action == Action.UNKNOWN)
      {
         // TODO: ERROR HANDLE
         Util.Sys.error("UNKNOWN ACTION...");
      }
      if (hand.busted())
      {
         Util.Sys.decorateSB(hand.getOwner().getName() + ": BUSTED with " + hand.asString() + " (" + hand.getValue()
               + ")");
      }
      return handComplete || hand.busted();
   }

   private void resolve(Hand hand)
   {
      HumanPlayer player = (HumanPlayer) hand.getOwner();
      StringBuilder resStr = new StringBuilder();
      Util.Tool.Result res = Util.Tool.buildResultString(dealerHand, hand, resStr);
      if (res == Util.Tool.Result.WIN)
      {
         player.setMoney(player.getMoney() + hand.getWager() * 2);
      }
      Util.Sys.decorateSB(resStr.toString());
      Util.Sys.displayMoney(player);
   }

}
