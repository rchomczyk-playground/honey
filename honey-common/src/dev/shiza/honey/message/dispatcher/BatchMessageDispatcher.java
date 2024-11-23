package dev.shiza.honey.message.dispatcher;

public interface BatchMessageDispatcher<VIEWER, RESULT>
    extends MessagePolyDispatcher<VIEWER, RESULT> {

  BatchMessageDispatcher<VIEWER, RESULT> add(
      final MessagePolyDispatcher<VIEWER, RESULT> dispatcher);

  BatchMessageDispatcher<VIEWER, RESULT> addAll(
      final MessagePolyDispatcher<VIEWER, RESULT>... dispatchers);
}
