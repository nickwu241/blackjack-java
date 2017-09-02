package com.nwu.backend;

import java.util.function.Function;

@FunctionalInterface
public interface BlackjackSystem {
   public void out (String msg, Function<String, String>... f);
}