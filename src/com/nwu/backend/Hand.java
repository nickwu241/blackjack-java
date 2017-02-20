package com.nwu.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represntation of a blackjack hand.
 */
public class Hand {
   //---------------------------------------------------------------------------
   private List<Card> cards_;

   //---------------------------------------------------------------------------
   public Hand() {
      cards_ = new ArrayList<>();
   }

   //---------------------------------------------------------------------------
   public Hand add(Card card) {
      cards_.add(card);
      return this;
   }

   //---------------------------------------------------------------------------
   public void clear() {
      cards_.clear();
   }

   //---------------------------------------------------------------------------
   public boolean splittable() {
      return cards_.size() == 2 &&
      cards_.get(0).getValue() == cards_.get(1).getValue();
   }

   //---------------------------------------------------------------------------
   public Hand split() {
      return new Hand().add(cards_.remove(1));
   }

   //---------------------------------------------------------------------------
   // gets the value of the hand, accounts for aces
   public int getValue() {
      long aces = cards_.stream().filter(card -> card.getValue() == 1).count();
      int total = cards_.stream().mapToInt(this::maxValue).sum();

      while (aces-- > 0)
         if (total > 21) total -= 10;

      return total;
   }

   //---------------------------------------------------------------------------
   public String string() {
      return cards_.stream()
                   .map(Card::string)
                   .collect(Collectors.joining(", "));
   }

   //---------------------------------------------------------------------------
   private int maxValue(Card card) {
      int val = card.getValue();
      if (val > 10) return 10;
      else if (val == 1) return 11;
      else return val;
   }
}
