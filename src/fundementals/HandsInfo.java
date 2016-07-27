package fundementals;

import java.util.List;

public class HandsInfo
{
   private boolean    multipleHands;
   private List<Hand> hands;
   private Hand       firstHand;

   public boolean multipleHands()
   {
      return multipleHands;
   }

   /**
    * 
    * @return null if empty() == true.
    */
   public Hand getFirstHand()
   {
      return firstHand;
   }

   /**
    * 
    * @return null if multipleHands() == false.
    */
   public List<Hand> getHandsAsList()
   {
      return hands;
   }

   /**
    * Only callable by BlackjackUtil.
    * 
    * @param empty
    * @param multipleHands
    * @param hands
    */
   HandsInfo(List<Hand> hands)
   {
      this.multipleHands = hands.size() > 1;
      firstHand = hands.get(0);
   }
}
