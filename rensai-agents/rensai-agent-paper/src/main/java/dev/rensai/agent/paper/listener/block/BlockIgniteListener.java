package dev.rensai.agent.paper.listener.block;

import dev.rensai.agent.common.grpc.GrpcClient;
import dev.rensai.agent.common.grpc.event.EventProperties;
import dev.rensai.agent.paper.PaperGameEvent;
import dev.rensai.agent.paper.listener.AbstractAgentListener;
import dev.rensai.agent.paper.mapper.MapperConstants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.plugin.Plugin;

public class BlockIgniteListener extends AbstractAgentListener {

  public BlockIgniteListener(Plugin plugin, GrpcClient grpcClient) {
    super(plugin, grpcClient);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockIgnite(BlockIgniteEvent event) {
    handleEvent(
        event,
        () -> {
          EventProperties properties =
              propertyBuilder()
                  .putMap(MapperConstants.BLOCK_KEY, mappers.mapBlock(event.getBlock()))
                  .putMap("igniting_block", mappers.mapBlock(event.getIgnitingBlock()))
                  .putMap("igniting_entity", mappers.mapEntity(event.getIgnitingEntity()))
                  .putString("ignite_cause", event.getCause().name())
                  .build();

          return new PaperGameEvent(event.getEventName(), properties);
        });
  }
}
