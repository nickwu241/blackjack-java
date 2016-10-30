package com.nwu.fundementals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Card
{
   public enum Suit
   {
      CLUBS,
      DIAMONDS,
      HEARTS,
      SPADES
   }
   
   private static Map<Integer, String> createValueToStringMap()
   {
      Map<Integer, String> m = new HashMap<>();
      m.put(1, "ACE");
      m.put(2, "TWO");
      m.put(3, "THREE");
      m.put(4, "FOUR");
      m.put(5, "FIVE");
      m.put(6, "SIX");
      m.put(7, "SEVEN");
      m.put(8, "EIGHT");
      m.put(9, "NINE");
      m.put(10, "TEN");
      m.put(11, "JACK");
      m.put(12, "QUEEN");
      m.put(13, "KING");
      return Collections.unmodifiableMap(m);
   }
   
   static private final Map<Integer, String> VALUE_TO_STRING = createValueToStringMap();
   private Integer                           value;
   private Suit                              suit;

   public Card(Suit suit, int value) throws IllegalArgumentException
   {
      if (value < 1 && value > 13)
      {
         throw new IllegalArgumentException("Not a valid suit or value.");
      }
      this.suit = suit;
      this.value = value;
   }

   public Suit getSuit()
   {
      return suit;
   }

   public int getValue()
   {
      return value;
   }

   public String toString()
   {
      return VALUE_TO_STRING.get(value) + " of " + suit;
   }
}