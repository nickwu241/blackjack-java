package fundementals.playerfactory;

import fundementals.BlackjackUtil;
import fundementals.Hand;

public class DealerAI extends BasicPlayer
{
   private Hand hand;
   DealerAI(String id, String name)
   {
      this.id = id;
      this.name = name;
      hand = BlackjackUtil.getInstance().createHand(null, this);
   }
   
   public Hand getHand(){
      return hand;
   }

   @Override
   public String getPlayerType()
   {
      return "dealer";
   }
}