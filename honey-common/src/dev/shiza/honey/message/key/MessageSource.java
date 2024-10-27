package dev.shiza.honey.message.key;

import java.util.Locale;
import java.util.Map;

public interface MessageSource {

  void register(final Locale locale, final Map<String, String> messages);

  String message(final Locale locale, final String key);
}
