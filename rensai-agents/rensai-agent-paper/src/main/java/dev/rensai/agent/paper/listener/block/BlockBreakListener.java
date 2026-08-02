package dev.rensai.agent.paper.listener.block;

import dev.rensai.agent.common.grpc.GrpcClient;
import dev.rensai.agent.common.grpc.event.EventProperties;
import dev.rensai.agent.paper.PaperGameEvent;
import dev.rensai.agent.paper.listener.AbstractAgentListener;
import dev.rensai.agent.paper.mapper.MapperConstants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.*;
import org.bukkit.plugin.Plugin;

public class BlockBreakListener extends AbstractAgentListener {

  public BlockBreakListener(Plugin plugin, GrpcClient grpcClient) {
    super(plugin, grpcClient);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockBreak(BlockBreakEvent event) {
    handleEvent(
        event,
        () -> {
          EventProperties properties =
              propertyBuilder()
                  .putMap(MapperConstants.BLOCK_KEY, mappers.mapBlock(event.getBlock()))
                  .putMap(MapperConstants.PLAYER_KEY, mappers.mapPlayer(event.getPlayer()))
                  .putString("exp_to_drop", String.valueOf(event.getExpToDrop()))
                  .putString("is_drop_items", String.valueOf(event.isDropItems()))
                  .build();

          return new PaperGameEvent(event.getEventName(), properties);
        });
  }
}
