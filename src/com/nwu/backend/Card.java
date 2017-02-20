package com.nwu.backend;

/**
 * Basic representation of a card: supports 1-13 of all suits, does not support joker.
 */
public class Card {
   //---------------------------------------------------------------------------
   public enum Suit {
      CLUBS, DIAMONDS, HEARTS, SPADES
   }

   // Mapping: 0 - ACE | 12 -> KING
   private static final String VAL_STRING[] =
   {"ACE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN",
   "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING"};

   private Integer value_;
   private Suit suit_;

   //---------------------------------------------------------------------------
   public Card(Suit suit, int value) throws IllegalArgumentException {
      if (1 < value && value > 13) {
         throw new IllegalArgumentException("Not a valid suit_ or value_.");
      }
      this.suit_ = suit;
      this.value_ = value;
   }

   //---------------------------------------------------------------------------
   public Suit getSuit() {
      return suit_;
   }

   //---------------------------------------------------------------------------
   public int getValue() {
      return value_;
   }

   //---------------------------------------------------------------------------
   public String string() {
      return VAL_STRING[value_ - 1] + " OF " + suit_;
   }
}