package com.nwu.fundementals;

public abstract class Player
{
   enum Type
   {
      HUMAN,
      DEALER
   }

   protected final String id_;
   protected String       name_;

   public Player(String id, String name)
   {
      id_ = id;
      name_ = name;
   }

   /**
    * Every player has a unique ID.
    * 
    * @return Unique ID of the Player.
    */
   public String getID()
   {
      return id_;
   }

   /**
    * Player names could have duplicates.
    * 
    * @return Name of the Player.
    */
   public String getName()
   {
      return name_;
   }

   /**
    * @return Type of the Player.
    */
   public abstract Type getType();

}