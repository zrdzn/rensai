package dev.rensai.agent.common.grpc.event.game;

import dev.rensai.agent.common.grpc.event.EventProperties;
import java.time.Instant;

public interface GameEvent {

  String getEventName();

  String getGameSource();

  Instant getTimestamp();

  EventProperties getProperties();
}
