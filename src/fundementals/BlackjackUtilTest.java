package fundementals;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.*;

import fundementals.blackjacktable.BlackjackTable;
import fundementals.playerfactory.HumanPlayer;
import fundementals.playerfactory.PlayerFactory;

public class BlackjackUtilTest
{
   private BlackjackUtil UTIL;
   private PlayerFactory playerFactory;
   private HumanPlayer nick;
   private Scanner in;
   
   /**
    * Sets up the test fixture. 
    * (Called before every test case method.)
    */
   @Before
   public void setUp() {
       UTIL = BlackjackUtil.getInstance();
       playerFactory = UTIL.getPlayerFactory();
       nick = (HumanPlayer)playerFactory.makePlayer("H", "54321", "Nick");
       in = new Scanner(System.in);
   }

   /**
    * Tears down the test fixture. 
    * (Called after every test case method.)
    */
   @After
   public void tearDown() {
      UTIL = null;
      playerFactory = null;
      in.close();
      in = null;
   }
   
   @Test (expected=NullPointerException.class)
   public void testCreateHand(){
      UTIL.createHand(null, nick);
   }
   
   @Test
   public void testPromptPlayerHand()
   {
      Map<Hand, Integer> handToWage = new HashMap<Hand, Integer>(); 
      nick.getHands().add(UTIL.createHand(handToWage, nick));
      Hand nickHand = nick.getHands().get(0);
      nickHand.add(new Card(Suit.DIAMONDS, 5));
      nickHand.add(new Card(Suit.DIAMONDS, 11));
      UTIL.promptPlayerHand(nickHand, in);
      assertNotEquals(nickHand, null);
   }

}
