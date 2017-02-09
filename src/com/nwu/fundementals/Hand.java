package com.nwu.fundementals;

import java.util.ArrayList;
import java.util.List;

public class Hand {
   private List<Card> cards_;
   private Player owner_;

   public Hand(Player owner) {
      cards_ = new ArrayList<>();
      owner_ = owner;
   }

   public Player getOwner() {
      return owner_;
   }

   public String asString() {
      String str = "";
      for (Card card : cards_) {
         str += card.toString() + ", ";
      }
      return str.substring(0, str.length() - 2);
   }

   public void add(Card card) {
      cards_.add(card);
   }

   public int size() {
      return cards_.size();
   }

   public boolean splittable() {
      return cards_.size() == 2 &&
             cards_.get(0).getValue() == cards_.get(1).getValue();
   }

   public Hand split() {
      Hand otherHand = new Hand(owner_);
      otherHand.add(cards_.remove(1));
      return otherHand;
   }

   public boolean busted() {
      return getValue() > 21;
   }

   // gets the value of the hand, accounts for aces
   public int getValue() {
      long aces = cards_.stream()
                        .filter(card -> card.getValue() == 1)
                        .count();

      int total = cards_.stream()
                        .mapToInt(this::maxValue)
                        .sum();

      while (aces-- > 0) {
         if (total > 21) {
            total -= 10;
         }
      }

      return total;
   }

   private int maxValue(Card card) {
      if (card.getValue() > 10) {
         return 10;
      }
      else if (card.getValue() == 1) {
         return 11;
      }
      else {
         return card.getValue();
      }
   }
}
