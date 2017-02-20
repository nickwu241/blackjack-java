package com.nwu.backend;

import com.nwu.client.Activity;

public class SimpleBlackjackSystem implements BlackjackSystem {
   @Override
   public void out(String msg) {
      Activity.appendText(msg + '\n');
   }
}
