package fundementals.playerfactory;

public class PlayerFactory
{
   /**
    * Player is null if given an invalid newPlayerType.
    * 
    * @param newPlayerType
    *           "D" for DealerAI, "H" for HumanPlayer.
    * @param id
    *           ID of player
    * @param name
    *           name of player
    * @return a certain Player
    */
   public BlackjackPlayer makePlayer(String newPlayerType, String id, String name)
   {
      BlackjackPlayer newPlayer = null;
      if (newPlayerType.equals("D"))
      {
         newPlayer = new DealerAI(id, name);
      }
      else if (newPlayerType.equals("H"))
      {
         newPlayer = new HumanPlayer(id, name);
      }
      else
      {
         System.out.println("Cannot create this Player");
      }
      return newPlayer;
   }
}
