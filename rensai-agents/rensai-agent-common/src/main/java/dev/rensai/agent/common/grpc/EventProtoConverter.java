package dev.rensai.agent.common.grpc;

import dev.rensai.grpc.GenericEventRequest;

public final class EventProtoConverter {
  private EventProtoConverter() {}

  public static GenericEventRequest toProto(GameEvent event) {
    return GenericEventRequest.newBuilder()
        .setEventName(event.getEventName())
        .setGameSource(event.getGameSource())
        .setTimestamp(event.getTimestamp().toEpochMilli())
        .putAllEventData(event.getProperties())
        .build();
  }
}
