package fundementals;

import java.util.LinkedList;
import java.util.List;

import fundementals.playerfactory.BlackjackPlayer;

public class Hand
{
   private List<Card>      cards;
   private BlackjackPlayer owner;
   private int             aces; // # of aces in hand

   /**
    * Only callable by BlackjackUtil.
    * 
    * @param owner
    *           Owner of hand, cannot change once Hand has been instantiated.
    */
   Hand(BlackjackPlayer owner)
   {
      cards = new LinkedList<Card>();
      aces = 0;
      this.owner = owner;
   }

   Hand(List<Card> cards, BlackjackPlayer owner)
   {
      this.cards = new LinkedList<Card>(cards);
      aces = 0;
      this.owner = owner;
   }
   
   public Card split(){
      return cards.remove(cards.size()-1);
   }

   // gets the value of the hand, accounts for aces
   public int getValue()
   {
      int handValue = 0;
      // adds up the max value of hand
      for (Card card : cards)
      {
         int cardValue = card.getValue();

         if (cardValue == 1)
         {
            cardValue = 11;
            aces++;
         }
         else if (cardValue > 10)
         {
            cardValue = 10;
         }

         handValue += cardValue;
      }
      // if bust because of ace, will choose ace to be of value 1 until no more
      // aces
      while (aces > 0)
      {
         if (handValue > 21)
         {
            handValue -= 10;
         }
         aces--;
      }
      return handValue;
   }

   public void add(Card card)
   {
      cards.add(card);
   }

   public void clear()
   {
      cards.clear();
   }

   public BlackjackPlayer getOwner()
   {
      return owner;
   }

   /**
    * @return every card on a new line, in a string.
    */
   public String asString()
   {
      String handToString = "";
      for (Card card : cards)
      {
         handToString += card.toString() + " ";
      }
      return handToString;
   }
}
