package fundementals.blackjacktable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import fundementals.*;
import fundementals.playerfactory.*;

public class BlackjackTable
{
   private final static BlackjackUtil UTIL      = BlackjackUtil.getInstance();
   private DealerAI                   dealer;
   private List<HumanPlayer>          players;
   private Deck                       deck;
   private Map<Hand, Integer>         handToWage;
   private int                        minWage;
   Scanner                            inScanner = new Scanner(System.in);

   // TODO: Generate random ID, Name
   public BlackjackTable()
   {
      this.dealer = (DealerAI) UTIL.getPlayerFactory().makePlayer("D", "12345", "Bob");
      this.players = new ArrayList<HumanPlayer>();
      this.deck = UTIL.createDeck52();
      this.handToWage = new HashMap<Hand, Integer>();
   }

   public void addPlayer(HumanPlayer player)
   {
      players.add(player);
   }

   public boolean removePlayer(HumanPlayer player)
   {
      return players.remove(player);
   }

   public void playRound()
   {
      clearHands();
      initRound();
      for (HumanPlayer player : players)
      {
         for (Hand hand : player.getHands())
         {
            UTIL.processHumanHand(hand, handToWage, deck, inScanner);
         }
      }
      dealer.getHand().add(deck.poll());
      if (dealer.getHand().getValue() == 21)
      {
         // TODO: Resolve dealer blackjack
         System.out.println("Dealer blackjack");
      }
      else
      {
         // TODO: Evaluate all hands and resolve wages
         System.out.println("Dealer has : " + dealer.getHand().getValue());
         System.out.println("You have : " + players.get(0).getHands().get(0).getValue());
      }
   }

   private void clearHands()
   {
      dealer.getHand().clear();
      for (HumanPlayer player : players)
      {
         player.getHands().clear();
      }
   }

   private void initRound()
   {
      deck = UTIL.createDeck52();
      dealer.getHand().add(deck.poll());
      // TODO: Simulate Casino by adding 1 card to each hand first
      // TODO: Change getHands() implementation in HumanPlayer
      for (HumanPlayer player : players)
      {
         player.setBet(UTIL.promptPlayerWager(player, this.minWage, inScanner));
         player.getHands().add(UTIL.createHand(handToWage, player));
         player.getHands().get(0).add(deck.poll());
         player.getHands().get(0).add(deck.poll());
      }
   }
}