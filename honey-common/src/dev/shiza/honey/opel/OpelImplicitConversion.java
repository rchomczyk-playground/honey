package dev.shiza.honey.opel;

import pl.allegro.tech.opel.OpelEngineBuilder;

public abstract class OpelImplicitConversion<T, R> {

  private final Class<T> from;
  private final Class<R> to;

  protected OpelImplicitConversion(Class<T> from, Class<R> to) {
    this.from = from;
    this.to = to;
  }

  public abstract R convert(final T from);

  public void register(final OpelEngineBuilder builder) {
    builder.withImplicitConversion(from, to, this::convert);
  }
}
