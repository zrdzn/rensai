package dev.rensai.agent.paper;

import dev.rensai.agent.common.grpc.event.AbstractGameEvent;
import dev.rensai.agent.common.grpc.event.EventProperties;

public class PaperGameEvent extends AbstractGameEvent {
  public PaperGameEvent(String eventName, EventProperties properties) {
    super(eventName, properties);
  }

  @Override
  public String getGameSource() {
    return "PAPER";
  }
}
