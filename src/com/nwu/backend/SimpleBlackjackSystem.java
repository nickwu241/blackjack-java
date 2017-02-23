package com.nwu.backend;

import com.nwu.client.view.Activity;

public class SimpleBlackjackSystem implements BlackjackSystem {
   @Override
   public void out (String msg) {
      Activity.appendText(msg + '\n');
   }

   @Override
   public void alert (String msg) {
      Activity.alert(msg);
   }
}
