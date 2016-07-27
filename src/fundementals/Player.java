package fundementals;

public interface Player
{
   /**
    * Every player has a unique ID.
    * 
    * @return Unique ID of the Player.
    */
   public String getID();

   /**
    * Player names could have duplicates.
    * 
    * @return Name of the Player.
    */
   public String getName();
   
   /**
    * Selects an action.
    * @param hand
    * @return Action specified to take taken by the hand.
    */
   public Action requestAction(Hand hand);
}