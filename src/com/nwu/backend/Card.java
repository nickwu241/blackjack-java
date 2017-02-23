package com.nwu.backend;

import java.util.Objects;

/**
 * Basic representation of a card: supports 1-13 of all suits, does not support joker.
 * <p>
 * Cards are equal if they have the same suit and same rank.
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

   private Integer rank_;
   private Suit suit_;

   //---------------------------------------------------------------------------
   public Card (Suit suit, int rank) throws IllegalArgumentException {
      if (1 < rank && rank > 13) {
         throw new IllegalArgumentException("Not a valid suit_ or rank_.");
      }
      this.suit_ = suit;
      this.rank_ = rank;
   }

   //---------------------------------------------------------------------------
   public Suit getSuit () {
      return suit_;
   }

   //---------------------------------------------------------------------------
   public int getValue () {
      return rank_;
   }

   //---------------------------------------------------------------------------
   public String string () {
      return VAL_STRING[rank_ - 1] + " OF " + suit_;
   }

   //---------------------------------------------------------------------------
   @Override
   public int hashCode () {
      return Objects.hash(rank_, suit_);
   }

   //---------------------------------------------------------------------------
   @Override
   public boolean equals (Object o) {
      return
      o != null && (
      o == this ||
      (o.getClass() == this.getClass() && ((Card) o).rank_ == rank_ && ((Card) o).suit_ == suit_)
      );
   }
}