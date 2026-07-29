package dev.rensai.agent.common.grpc;

import dev.rensai.common.CommonConstants;
import java.time.Instant;
import java.util.Map;

public abstract class AbstractGameEvent implements GameEvent {

  private final String eventName;
  private final Instant timestamp;
  private final Map<String, String> properties;

  public AbstractGameEvent(String eventName, Map<String, String> properties) {
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
  public Map<String, String> getProperties() {
    return Map.copyOf(properties);
  }
}
