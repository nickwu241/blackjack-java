package com.nwu.fundementals;

/**
 * Basic representation of a card: supports 1-13 of all suits, does not support joker.
 */
public class Card {
   public enum Suit {
      CLUBS,
      DIAMONDS,
      HEARTS,
      SPADES
   }

   private static final String VAL_STRING[] =
   {"ACE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN",
   "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING"};

   private Integer value;
   private Suit suit;

   public Card(Suit suit, int value) throws IllegalArgumentException {
      if (value < 1 && value > 13) {
         throw new IllegalArgumentException("Not a valid suit or value.");
      }
      this.suit = suit;
      this.value = value;
   }

   public Suit getSuit() {
      return suit;
   }

   public int getValue() {
      return value;
   }

   public String toString() {
      return VAL_STRING[value - 1] + " of " + suit;
   }
}