package fundementals.playerfactory;

public abstract class BasicPlayer implements BlackjackPlayer
{
   protected String     id;
   protected String     name;

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
}
