package com.nwu.backend;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageHelper {
   //---------------------------------------------------------------------------
   private final static String kCARD_IMAGES_DIR = "images/PNG-cards-1.3/";
   private final static String kPNG = ".png";
   private final static String kJPG = ".jpg";

   private final static ImageHelper instance_ = new ImageHelper();
   private final Map<Card, Image> cardsFront_;

   private final Image cardBack_;

   //---------------------------------------------------------------------------
   public static ImageHelper instance () {
      return instance_;
   }

   //---------------------------------------------------------------------------
   public Image imageForCard (Card card) {
      assert (card != null);
      assert (cardsFront_.get(card) != null);

      return cardsFront_.get(card);
   }

   public Image imageForIcon () {
      return imageFromPath(kCARD_IMAGES_DIR + "21_icon" + kJPG);
   }

   public Image imageForCardBack () {
      return cardBack_;
   }

   //---------------------------------------------------------------------------
   private ImageHelper () {
      cardBack_ = imageFromPath(kCARD_IMAGES_DIR + "back" + kPNG);
      cardsFront_ = new HashMap<>(52);
      for (Card.Suit suit : Card.Suit.values()) {
         for (int rank = 1; rank <= 13; rank++) {
            // TODO: maybe better way to load the cards
            Card card = new Card(suit, rank);
            String p = kCARD_IMAGES_DIR + card.string().replace(' ', '_').toLowerCase() + kPNG;
            cardsFront_.put(card, imageFromPath(p));
         }
      }

      assert (cardsFront_.size() == 52);
   }

   //---------------------------------------------------------------------------
   private Image imageFromPath (String name) {
      return new Image(getClass().getResourceAsStream(name));
   }
}
