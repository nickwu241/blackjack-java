package fundementals;

public class Dealer implements Player
{
   private final static int THINK_TIME_MS = 1000;
   private final String     id;
   private String           name;

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
      try
      {
         Thread.sleep(THINK_TIME_MS);
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }
      
      if (hand.getValue() < 17)
      {
         return Action.HIT;
      }
      else
      {
         return Action.STAY;
      }
   }
}
