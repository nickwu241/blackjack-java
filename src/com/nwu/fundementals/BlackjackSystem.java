package com.nwu.fundementals;

import com.nwu.client.Activity;

public class BlackjackSystem {
   private static Activity activity_ = new Activity();

   public static void out(String msg) {
      activity_.appendText(msg + '\n');
   }

   public static Activity getActivity() {
      return activity_;
   }
}
