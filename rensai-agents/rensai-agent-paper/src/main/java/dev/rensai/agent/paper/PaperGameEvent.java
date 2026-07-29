package dev.rensai.agent.paper;

import dev.rensai.agent.common.grpc.AbstractGameEvent;
import java.util.Map;

public class PaperGameEvent extends AbstractGameEvent {
  public PaperGameEvent(String eventName, Map<String, String> properties) {
    super(eventName, properties);
  }

  @Override
  public String getGameSource() {
    return "PAPER";
  }
}
