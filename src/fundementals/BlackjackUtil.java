package fundementals;

import java.util.Map;
import java.util.Scanner;

import fundementals.playerfactory.BlackjackPlayer;
import fundementals.playerfactory.HumanPlayer;
import fundementals.playerfactory.PlayerFactory;

//TODO: SPLIT EXECEPTIONS

/**
 * This is a Singleton class for Blackjack.
 * 
 * @author Nick
 *
 */
public class BlackjackUtil
{
   private static BlackjackUtil instance;
   private PlayerFactory        playerFactory;

   // Returns success or not
   public boolean processHumanHand(Hand hand, Map<Hand, Integer> handToWage, Deck deck, Scanner scanner)
   {
      if (!hand.getOwner().getPlayerType().equals("human"))
      {
         System.out.println("ERROR : processing a non-humanplayer hand");
         return false;
      }
      boolean notDone = true;
      HumanPlayer player = (HumanPlayer) hand.getOwner();
      int handWage = handToWage.get(hand);
      while (notDone || hand.getValue() <= 21)
      {
         Action playerAction = promptPlayerHand(hand, scanner);
         if (playerAction == Action.HIT)
         {
            hand.add(deck.poll());
         }
         else if (playerAction == Action.STAY)
         {
            notDone = false;
         }
         else if (playerAction == Action.DOUBLEDOWN)
         {
            if (player.getMoney() < handWage)
            {
               System.out.println("ERROR : Insufficent money to doubledown");
            }
            else
            {
               hand.add(deck.poll());
               notDone = false;
            }

         }
         else if (playerAction == Action.SPLIT)
         {
            if (player.getMoney() < handWage)
            {
               System.out.println("ERROR : Insufficent money to split");
            }
            else
            {
               Hand secondHand = instance.createHand(handToWage, player);
               secondHand.add(hand.split());
               player.getHands().add(secondHand);
               processHumanHand(secondHand, handToWage, deck, scanner);
            }
         }
      }
      assert (hand.getValue() > 0);
      return true;
   }

   // TODO: Probably not needed after GUI
   public void printOwnerHand(Hand hand)
   {
      System.out.println(hand.getOwner().getName() + " has : " + hand.asString());
   }

   /**
    * 
    * @param hand
    * @return Action.NONE if user specified an unknown action
    */
   public Action promptPlayerHand(Hand hand, Scanner scanner)
   {
      printOwnerHand(hand);
      System.out.println("Select an Action : STAY, HIT, DD (double down), SPLIT)");
      String input;
      // TODO: FIX LOGIC, bugs out atm
      while (scanner.hasNextLine())
      {
         input = scanner.nextLine();
         if (input.equals("STAY"))
         {
            return Action.STAY;
         }
         else if (input.equals("HIT"))
         {
            return Action.HIT;
         }
         else if (input.equals("DD"))
         {
            return Action.DOUBLEDOWN;
         }
         else if (input.equals("SPLIT"))
         {
            return Action.SPLIT;
         }
         // TODO: once GUI, don't need this else
         else
         {
            System.out.println("INVALID SELECTION, please select : STAY, HIT, DD (double down), SPLIT");
            return Action.NONE;
         }
      }
      // ShHOULD NEVER REACH HERE, return statement to make compiler happy?
      assert (1 == 0);
      return null;
   }

   public int promptPlayerWager(HumanPlayer player, int minWager, Scanner scanner)
   {
      int input = 0;
      System.out.println(player.getName() + "::" + "Enter wager amount :");
      input = scanner.nextInt();
      while (true)
      {
         if (input < minWager)
         {
            System.out.println("Please enter a number above the minimum wager (" + minWager + ") : ");
         }
         else if (input > player.getMoney())
         {
            System.out.println("You don't have enough money (enter <" + player.getMoney() + ") :");
         }
         else
         {
            return input;
         }
         input = scanner.nextInt();
         scanner.nextLine(); // consume the line
      }
   }

   public PlayerFactory getPlayerFactory()
   {
      return playerFactory;
   }

   // TODO: MAY NEED TO CHNAGE
   /**
    * @throws NullPointerException
    *            if passed null as @param handToWage AND the player is a
    *            HumanPlayer.
    * @param handToWage
    * @param owner
    *           owner of the hand
    * @return a new empty hand with owner set to @param owner
    */
   public Hand createHand(Map<Hand, Integer> handToWage, BlackjackPlayer owner)
   {
      Hand newHand = new Hand(owner);
      if (owner.getPlayerType().equals("human"))
      {
         handToWage.put(newHand, ((HumanPlayer) owner).getBet());
      }
      return newHand;
   }

   /**
    * 
    * @return Shuffled deck of a regular 52 card deck.
    */
   public Deck createDeck52()
   {
      Deck deck52 = new Deck();
      for (Suit suit : Suit.values())
      {
         for (int value = 1; value <= 13; value++)
         {
            try
            {
               deck52.add(new Card(suit, value));
            }
            catch (IllegalArgumentException e)
            {
               e.printStackTrace();
            }
         }
      }
      deck52.shuffle();
      return deck52;
   }

   // Double check implementation to ensure thread-safety
   public static BlackjackUtil getInstance()
   {
      if (instance == null)
      {
         synchronized (BlackjackUtil.class)
         {
            if (instance == null)
            {
               instance = new BlackjackUtil();
            }
         }
      }
      return instance;
   }

   // Singleton Pattern : private constructor
   private BlackjackUtil()
   {
      playerFactory = new PlayerFactory();
   }
}
