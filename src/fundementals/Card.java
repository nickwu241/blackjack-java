package fundementals;

import java.util.HashMap;
import java.util.Map;

public class Card
{
   private Integer value;
   private Suit    suit;

   static private final Map<Integer, String> VALUE_TO_STRING = bjValueToStringMap();

   private static Map<Integer, String> bjValueToStringMap()
   {
      Map<Integer, String> bjCardValueToString = new HashMap<>();
      bjCardValueToString.put(1, "ACE");
      bjCardValueToString.put(2, "TWO");
      bjCardValueToString.put(3, "THREE");
      bjCardValueToString.put(4, "FOUR");
      bjCardValueToString.put(5, "FIVE");
      bjCardValueToString.put(6, "SIX");
      bjCardValueToString.put(7, "SEVEN");
      bjCardValueToString.put(8, "EIGHT");
      bjCardValueToString.put(9, "NINE");
      bjCardValueToString.put(10, "TEN");
      bjCardValueToString.put(11, "JACK");
      bjCardValueToString.put(12, "QUEEN");
      bjCardValueToString.put(13, "KING");
      return bjCardValueToString;
   }

   Card(Suit suit, int value) throws IllegalArgumentException
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