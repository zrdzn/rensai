package dev.rensai.agent.paper.listener;

import dev.rensai.agent.common.grpc.EventProtoConverter;
import dev.rensai.agent.common.grpc.GameEvent;
import dev.rensai.agent.common.grpc.GrpcClient;
import dev.rensai.grpc.GenericEventRequest;
import java.util.function.Function;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAgentListener implements Listener {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAgentListener.class);

  protected final Plugin plugin;

  private final GrpcClient grpcClient;

  protected AbstractAgentListener(Plugin plugin, GrpcClient grpcClient) {
    this.plugin = plugin;
    this.grpcClient = grpcClient;
  }

  protected <T extends Event> void handleEvent(T event, Function<T, GameEvent> mapper) {
    try {
      GameEvent commonEvent = mapper.apply(event);
      GenericEventRequest request = EventProtoConverter.toProto(commonEvent);

      plugin
          .getServer()
          .getAsyncScheduler()
          .runNow(
              plugin,
              _ -> {
                try {
                  grpcClient.sendGenericEvent(request);
                } catch (Exception ex) {
                  LOGGER.error("Failed to dispatch gRPC event: {}", ex.getMessage());
                }
              });
    } catch (Exception ex) {
      LOGGER.error("Failed to map event {}: {}", event.getEventName(), ex.getMessage());
    }
  }
}
