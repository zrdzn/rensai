package dev.rensai.agent.common.grpc;

import java.time.Instant;
import java.util.Map;

public interface GameEvent {

  String getEventName();

  String getGameSource();

  Instant getTimestamp();

  Map<String, String> getProperties();
}
