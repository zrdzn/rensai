package dev.rensai.agent.paper.listener;

import dev.rensai.agent.common.grpc.GrpcClient;
import dev.rensai.agent.common.grpc.event.EventPropertiesBuilder;
import dev.rensai.agent.common.grpc.event.game.GameEvent;
import dev.rensai.agent.paper.mapper.MapperRegistry;
import java.util.function.Supplier;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAgentListener implements Listener {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAgentListener.class);

  protected final Plugin plugin;
  protected final MapperRegistry mappers;

  private final GrpcClient grpcClient;

  protected AbstractAgentListener(Plugin plugin, GrpcClient grpcClient) {
    this.plugin = plugin;
    this.grpcClient = grpcClient;
    this.mappers = new MapperRegistry();
  }

  protected <T extends Event> void handleEvent(T event, Supplier<GameEvent> eventSupplier) {
    try {
      GameEvent gameEvent = eventSupplier.get();

      plugin
          .getServer()
          .getAsyncScheduler()
          .runNow(
              plugin,
              _ -> {
                try {
                  grpcClient.sendGenericEvent(gameEvent);
                } catch (Exception ex) {
                  LOGGER.error("Failed to dispatch gRPC event: {}", ex.getMessage());
                }
              });
    } catch (Exception ex) {
      LOGGER.error("Failed to map event {}: {}", event.getEventName(), ex.getMessage());
    }
  }

  protected EventPropertiesBuilder propertyBuilder() {
    return new EventPropertiesBuilder();
  }
}
