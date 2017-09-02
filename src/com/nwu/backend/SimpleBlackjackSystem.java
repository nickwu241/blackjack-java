package com.nwu.backend;

import java.util.function.Function;
import java.util.stream.Stream;

public class SimpleBlackjackSystem implements BlackjackSystem {
   @Override
   @SafeVarargs
   public final void out (String msg, Function<String, String>... f) {
      Stream.of(f).reduce(Function.identity(), Function::andThen).apply(msg);
   }
}
