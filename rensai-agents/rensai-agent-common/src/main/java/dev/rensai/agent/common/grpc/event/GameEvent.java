package dev.rensai.agent.common.grpc.event;

import java.time.Instant;

public interface GameEvent {

  String getEventName();

  String getGameSource();

  Instant getTimestamp();

  EventProperties getProperties();
}
