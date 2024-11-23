package dev.shiza.honey.adventure.message.dispatcher;

public final class AdventureMessageConfigurers {

  private AdventureMessageConfigurers() {}

  public static AdventureMessageConfigurer createChat() {
    return new AdventureMessageConfigurer(AdventureMessageDelivery.CHAT);
  }

  public static AdventureMessageConfigurer createActionBar() {
    return new AdventureMessageConfigurer(AdventureMessageDelivery.ACTION_BAR);
  }

  public static AdventureTitleMessageConfigurer createTitle() {
    return new AdventureTitleMessageConfigurer();
  }

  public static AdventureBatchMessageConfigurer createBatch() {
    return new AdventureBatchMessageConfigurer();
  }
}
