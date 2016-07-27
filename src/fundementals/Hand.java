package fundementals;

import java.util.ArrayList;
import java.util.List;

public class Hand
{
   private List<Card> cards;
   private int        aces; // # of aces in hand
   private Player     owner;

   public Hand(Player owner)
   {
      cards = new ArrayList<>();
      this.owner = owner;
   }

   public Player getOwner()
   {
      return owner;
   }

   /**
    * 
    */
   public Action requestAction()
   {
      return owner.requestAction(this);
   }

   /**
    * @return every card on a new line, in a string.
    */
   public String asString()
   {
      String str = "";
      for (Card card : cards)
      {
         str += card.toString() + ", ";
      }
      return str.substring(0, str.length()-2);
   }

   public void add(Card card)
   {
      cards.add(card);
   }

   public Hand split()
   {
      Hand otherHand = new Hand(this.owner);
      otherHand.add(cards.remove(1));
      return otherHand;
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
}
