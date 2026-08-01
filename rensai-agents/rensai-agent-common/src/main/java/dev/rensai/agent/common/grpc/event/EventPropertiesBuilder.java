package dev.rensai.agent.common.grpc.event;

import java.util.HashMap;
import java.util.Map;

public class EventPropertiesBuilder {
  private final Map<String, Object> properties;

  public EventPropertiesBuilder() {
    this.properties = new HashMap<>();
  }

  public EventPropertiesBuilder putMap(String key, Map<String, ?> nestedMap) {
    this.properties.put(key, nestedMap);
    return this;
  }

  public EventPropertiesBuilder put(String key, String value) {
    properties.put(key, value);
    return this;
  }

  public EventProperties build() {
    return new EventProperties(properties);
  }
}
