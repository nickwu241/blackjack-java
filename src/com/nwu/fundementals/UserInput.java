package com.nwu.fundementals;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput
{
   private final static Tool UTIL = new Tool();

   public int promptWage()
   {
      System.out.println("Enter your wager amount: ");
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
