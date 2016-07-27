package fundementals.playerfactory;

public interface BlackjackPlayer
{

   /**
    * Every player has a unique ID.
    * 
    * @return unique ID of the Player.
    */
   public String getID();

   /**
    * Player names could have duplicates.
    * 
    * @return Name of the Player.
    */
   public String getName();
   
   /**
    * Basic playerTypes are dealer and human
    * 
    * @return returns the type of player in a String.
    */
   public String getPlayerType();
}