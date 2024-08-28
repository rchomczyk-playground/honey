package dev.shiza.honey.processor;

@FunctionalInterface
public interface Processor {

  String process(final String content);
}
