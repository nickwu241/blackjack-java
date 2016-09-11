package com.nwu.fundementals;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput
{
   private final static Tool UTIL = new Tool();

   public int promptWage(int minWager)
   {
      System.out.println("Enter your wager amount (minimum " + minWager + "):");
      Scanner scan = new Scanner(System.in);
      while (!scan.hasNextLine());
      while (true)
      {
         try
         {
            int input = scan.nextInt();
            if (input < 0)
            {
               UTIL.error("Please enter a positive number");
            }
            else if (input < minWager)
            {
               UTIL.error("The minimum wager is " + minWager);
            }
            else
            {
               return input;
            }
         }
         catch (InputMismatchException ex)
         {
            UTIL.error("Enter an Integer");
         }
      }
   }
}
