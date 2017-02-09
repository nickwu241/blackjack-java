package com.nwu.fundementals;

import com.nwu.client.Activity;

import java.util.*;

public class Table {
   //---------------------------------------------------------------------------
   public static void main(String args[]) {
      ArrayList<GenericPlayer> players = new ArrayList<>();
      players.add(new GenericPlayer("random", "Nick"));
      players.get(0).setMoney(1000);
      Table table = new Table(10, players);
      table.newRound();
   }

   private class Sys {
      // private Scanner in = new Scanner(System.in);
      Activity outStream = null;
      private volatile Action action = Action.UNKNOWN;

      private void out(String message) {
         if (outStream != null) {
            outStream.appendText(message + '\n');
         }
         else {
            System.out.println(message);
         }
      }

      private String money(GenericPlayer player, int amount) {
         String paidOrReceived = "";
         if (amount >= 0) {
            paidOrReceived = "received";
         }
         else {
            paidOrReceived = "paid";
            amount *= -1;
         }
         return player.getName() + " " + paidOrReceived + " $" + amount
                + "... $" + player.getMoney() + " remaining";
      }

      private String hand(Hand hand) {
         return hand.getOwner().getName() + " has " + hand.asString()
                + " (" + hand.getValue() + ")";
      }

      private String drew(Player player, Card card) {
         return player.getName() + " drew " + card.toString();
      }

      private String choices(Hand hand) {
         String selectChoices = "Select an Action: HIT, STAY";
         if (hand.size() == 2) {
            selectChoices += ", DD";
         }
         if (hand.splittable()) {
            selectChoices += ", SPLIT";
         }
         return selectChoices;
      }

      private String result(Hand playerHand, String result) {
         return
         playerHand.getOwner().getName() + " "
         + result + " with " + playerHand.asString()
         + " (" + playerHand.getValue() + ")";
      }

      private Action promptChoice(Hand hand) {
         while (action == Action.UNKNOWN);
         switch (action) {
            case HIT: action = Action.UNKNOWN; return Action.HIT;
            case STAY: action = Action.UNKNOWN; return Action.STAY;
            case DOUBLEDOWN: action = Action.UNKNOWN; return Action.DOUBLEDOWN;
            case SPLIT: action = Action.UNKNOWN; return Action.SPLIT;
            default:
               // TODO: Handle this case
               throw new RuntimeException("Unknown Action...?");
         }

         /*
         while (true) {
            String input = in.nextLine().toUpperCase();
            if ((input.equals("HIT") || input.equals("STAY"))
                || (input.equals("DD") && hand.size() == 2)
                || (input.equals("SPLIT") && hand.splittable())) {
               switch (input) {
                  case "HIT": return Action.HIT;
                  case "STAY": return Action.STAY;
                  case "DD": return Action.DOUBLEDOWN;
                  case "SPLIT": return Action.SPLIT;
                  default:
                     break;
               }
            }
            out(input + " is not an option. Try again");
         }
         */
      }
   }

   //---------------------------------------------------------------------------
   private int minWager_;
   private Deck deck_;
   private Map<Hand, Integer> handWagerMap_;
   private Hand dealerHand_;
   private List<GenericPlayer> players_;
   private Sys sys_;

   public void setActivity(Activity activity) {
      sys_.outStream = activity;
   }

   public void setAction(Action action) {
      sys_.action = action;
   }

   public Table(int minWager, List<GenericPlayer> players) {
      minWager_ = minWager;
      deck_ = getStandardDeck();
      handWagerMap_ = new HashMap<>();
      dealerHand_ = new Hand(new Dealer("ID", "Dealer"));
      players_ = new ArrayList<>(players);
      sys_ = new Sys();
   }

   public void newRound() {
      players_.removeIf(player -> player.getMoney() < minWager_);
      deck_ = getStandardDeck();
      handWagerMap_.clear();
      dealerHand_ = new Hand(dealerHand_.getOwner());
      players_.forEach(player -> createHand(player, minWager_));

      for (Hand hand : handWagerMap_.keySet()) {
         hand.add(deck_.drawTop());
         hand.add(deck_.drawTop());
      }
      dealerHand_.add(deck_.drawTop());

      handWagerMap_.keySet().forEach(this::play);
      dealerAI();
      handWagerMap_.keySet().forEach(this::getResults);
   }

   private void play(Hand hand) {
      sys_.out(sys_.hand(dealerHand_));
      sys_.out(sys_.hand(hand));

      boolean done = false;
      while (!done) {
         sys_.out(sys_.choices(hand));
         Action action = sys_.promptChoice(hand);
         switch (action) {
            case HIT:
               Card draw = deck_.drawTop();
               sys_.out(sys_.drew(hand.getOwner(), draw));
               hand.add(draw);
               if (hand.busted()) {
                  done = true;
               }
               break;

            case SPLIT:
               // TODO:
               break;

            case DOUBLEDOWN:
               int extra = handWagerMap_.get(hand);
               if (!addMoneyToHand(hand, extra)) {
                  sys_.out(hand.getOwner()
                               .getName() + " doesn't have enough money to DD. Try again");
                  break;
               }
               Card card = deck_.drawTop();
               sys_.out(sys_.drew(hand.getOwner(), card));
               hand.add(card);
            case STAY:
               done = true;
               break;
         }
      }

      if (hand.busted()) {
         sys_.out("Busted!");
      }
   }

   private void getResults(Hand hand) {
      sys_.out(sys_.hand(dealerHand_));
      GenericPlayer player = (GenericPlayer) hand.getOwner();
      if ((!dealerHand_.busted() && hand.getValue() < dealerHand_.getValue())
          || hand.busted()) {
         sys_.out(sys_.result(hand, "LOST"));
      }
      else if (hand.getValue() == dealerHand_.getValue()) {
         sys_.out(sys_.result(hand, "TIED"));
         addMoneyToPlayer(player, handWagerMap_.get(hand));
      }
      else {
         sys_.out(sys_.result(hand, "WON"));
         addMoneyToPlayer(player, handWagerMap_.get(hand) * 2);
      }
   }

   private void dealerAI() {
      try {
         Thread.sleep(Dealer.THINK_TIME_MS);
      }
      catch (InterruptedException e) {
         e.printStackTrace();
      }

      while (dealerHand_.getValue() < 17) {
         Card draw = deck_.drawTop();
         sys_.out(sys_.drew(dealerHand_.getOwner(), draw));
         dealerHand_.add(draw);
      }
   }

   private void createHand(GenericPlayer player, int wager) {
      Hand hand = new Hand(player);
      if (!addMoneyToHand(hand, wager)) {
         sys_.out(player.getName() + " doesn't have enough money to play");
      }
      // TODO: What happens if they don't have enough money.
   }

   private boolean addMoneyToHand(Hand hand, int amount) {
      GenericPlayer player = (GenericPlayer) hand.getOwner();
      if (addMoneyToPlayer(player, -amount)) {
         int total = amount;
         if (handWagerMap_.get(hand) != null) {
            total += handWagerMap_.get(hand);
         }
         handWagerMap_.put(hand, total);
         return true;
      }
      else {
         return false;
      }
   }

   private boolean addMoneyToPlayer(GenericPlayer player, int amount) {
      if (player.getMoney() > -amount) {
         player.setMoney(player.getMoney() + amount);
         sys_.out(sys_.money(player, amount));
         return true;
      }
      else {
         return false;
      }
   }

   private static Deck getStandardDeck() {
      List<Card> cards = new ArrayList<>(52);
      for (Card.Suit suit : Card.Suit.values()) {
         for (int val = 1; val <= 13; val++) {
            cards.add(new Card(suit, val));
         }
      }
      Collections.shuffle(cards);
      return new Deck(cards);
   }
}
