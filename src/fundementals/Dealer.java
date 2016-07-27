package fundementals;

public class Dealer implements Player
{
   private final String id;
   private String       name;

   public Dealer(String id, String name)
   {
      this.id = id;
      this.name = name;
   }

   @Override
   public String getID()
   {
      return id;
   }

   @Override
   public String getName()
   {
      return name;
   }

   @Override
   public Action requestAction(Hand hand)
   {
      if(hand.getValue() < 17){
         return Action.HIT;
      }
      else {
         return Action.STAY;
      }
   }
}
