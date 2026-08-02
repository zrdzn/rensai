package dev.rensai.agent.common.grpc.event;

import dev.rensai.agent.common.grpc.GrpcStructConverter;
import dev.rensai.agent.common.grpc.event.game.GameEvent;
import dev.rensai.grpc.GenericEventRequest;
import dev.rensai.grpc.SupportedEventsRequest;

public final class EventProtoConverter {
  private EventProtoConverter() {}

  public static GenericEventRequest toProto(GameEvent event) {
    return GenericEventRequest.newBuilder()
        .setEventName(event.getEventName())
        .setGameSource(event.getGameSource())
        .setTimestamp(event.getTimestamp().toEpochMilli())
        .setProperties(GrpcStructConverter.toStruct(event.getProperties().map()))
        .build();
  }

  public static SupportedEventsRequest toProto(SupportedEvents supportedEvents) {
    return SupportedEventsRequest.newBuilder()
        .setAgentId(supportedEvents.agentId())
        .setAgentType(supportedEvents.agentType())
        .addAllEventNames(supportedEvents.eventNames())
        .build();
  }
}
