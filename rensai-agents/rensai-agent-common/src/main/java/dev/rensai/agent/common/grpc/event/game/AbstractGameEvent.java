package dev.rensai.agent.common.grpc.event.game;

import dev.rensai.agent.common.grpc.event.EventProperties;
import dev.rensai.common.CommonConstants;
import java.time.Instant;

public abstract class AbstractGameEvent implements GameEvent {

  private final String eventName;
  private final Instant timestamp;
  private final EventProperties properties;

  public AbstractGameEvent(String eventName, EventProperties properties) {
    this.eventName = eventName;
    this.timestamp = Instant.now(CommonConstants.CLOCK);
    this.properties = properties;
  }

  @Override
  public String getEventName() {
    return eventName;
  }

  @Override
  public Instant getTimestamp() {
    return timestamp;
  }

  @Override
  public EventProperties getProperties() {
    return properties;
  }
}
