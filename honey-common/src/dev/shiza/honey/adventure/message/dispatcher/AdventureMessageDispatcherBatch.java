package dev.shiza.honey.adventure.message.dispatcher;

import dev.shiza.honey.message.dispatcher.MessageDispatcherBatch;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.audience.Audience;

public final class AdventureMessageDispatcherBatch
    extends MessageDispatcherBatch<Audience, AdventureMessageDispatcher> {

  private AdventureMessageDispatcherBatch(final List<AdventureMessageDispatcher> dispatchers) {
    super(dispatchers);
  }

  public static AdventureMessageDispatcherBatch newBatch() {
    return new AdventureMessageDispatcherBatch(new ArrayList<>());
  }

  @Override
  public AdventureMessageDispatcherBatch append(final AdventureMessageDispatcher dispatcher) {
    super.append(dispatcher);
    return this;
  }

  @Override
  public AdventureMessageDispatcherBatch recipient(final Audience recipient) {
    super.recipient(recipient);
    return this;
  }
}
