package com.nwu.fundementals;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Deck {
   LinkedList<Card> deck_;

   /**
    * Creates an empty Deck.
    */
   public Deck() {
      deck_ = new LinkedList<Card>();
   }

   /**
    * Creates a deck_ filled with cards.
    *
    * @param cards
    */
   public Deck(List<Card> cards) {
      deck_ = new LinkedList<Card>(cards);
   }

   public void addTop(Card card) {
      deck_.add(card);
   }

   public Card drawTop() {
      return deck_.poll();
   }

   public void shuffle() {
      Collections.shuffle(deck_);
   }

   public void removeRandomCards(int numCards) {
      Random ran = new Random();
      while (numCards-- > 0) {
         deck_.remove(ran.nextInt(numCards));
      }
   }
}