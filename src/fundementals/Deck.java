package fundementals;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Deck
{
   LinkedList<Card> deck;

   /**
    * Creates an empty Deck.
    */
   Deck()
   {
      deck = new LinkedList<Card>();
   }

   /**
    * Creates a deck filled with cards.
    * @param cards
    */
   Deck(List<Card> cards)
   {
      deck = new LinkedList<Card>(cards);
   }

   public void add(Card card)
   {
      deck.add(card);
   }

   public void remove()
   {
      deck.remove();
   }

   public Card poll()
   {
      return deck.poll();
   }

   public void shuffle()
   {
      Collections.shuffle(deck);
   }

   public void removeRandomCards(int numCards)
   {
      Random ran = new Random();
      while (--numCards >= 0)
      {
         deck.remove(ran.nextInt(numCards));
      }
   }
}