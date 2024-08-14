package dev.shiza.honey.opel;

import java.util.HashSet;
import java.util.Set;
import pl.allegro.tech.opel.OpelEngineBuilder;

public class OpelImplicitConversionBatch {

  private final Set<OpelImplicitConversion<?, ?>> conversions;

  OpelImplicitConversionBatch() {
    this.conversions = new HashSet<>();
  }

  public static OpelImplicitConversionBatch create() {
    return new OpelImplicitConversionBatch();
  }

  public OpelImplicitConversionBatch add(final OpelImplicitConversion<?, ?> conversion) {
    conversions.add(conversion);
    return this;
  }

  public void register(final OpelEngineBuilder engineBuilder) {
    conversions.forEach(conversion -> conversion.register(engineBuilder));
  }
}
