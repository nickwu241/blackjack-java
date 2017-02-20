package com.nwu.backend;

import java.util.*;

public class Deck {
   //---------------------------------------------------------------------------
   private LinkedList<Card> deck_;

   //---------------------------------------------------------------------------
   public void addTop(Card card) {
      deck_.add(card);
   }

   //---------------------------------------------------------------------------
   public Card drawTop() {
      return deck_.poll();
   }

   //---------------------------------------------------------------------------
   public void shuffle() {
      Collections.shuffle(deck_);
   }

   //---------------------------------------------------------------------------
   public void removeRandomCards(int numCards) {
      Random ran = new Random();
      while (numCards-- > 0) {
         deck_.remove(ran.nextInt(numCards));
      }
   }

   //---------------------------------------------------------------------------
   public static Deck standard52Deck() {
      List<Card> deck = new ArrayList<>(52);
      for (Card.Suit suit : Card.Suit.values()) {
         for (int val = 1; val <= 13; val++) {
            deck.add(new Card(suit, val));
         }
      }
      Collections.shuffle(deck);
      return new Deck(deck);
   }


   /**
    * Creates a deck filled with cards (preserves ordering).
    *
    * @param cards
    */
   private Deck(List<Card> cards) {
      deck_ = new LinkedList<>(cards);
   }
}