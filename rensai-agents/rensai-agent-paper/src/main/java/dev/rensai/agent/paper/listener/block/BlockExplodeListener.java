package dev.rensai.agent.paper.listener.block;

import dev.rensai.agent.common.grpc.GrpcClient;
import dev.rensai.agent.common.grpc.event.EventProperties;
import dev.rensai.agent.paper.PaperGameEvent;
import dev.rensai.agent.paper.listener.AbstractAgentListener;
import dev.rensai.agent.paper.mapper.MapperConstants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.plugin.Plugin;

public class BlockExplodeListener extends AbstractAgentListener {

  public BlockExplodeListener(Plugin plugin, GrpcClient grpcClient) {
    super(plugin, grpcClient);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockExplode(BlockExplodeEvent event) {
    handleEvent(
        event,
        () -> {
          EventProperties properties =
              propertyBuilder()
                  .putMap(MapperConstants.BLOCK_KEY, mappers.mapBlock(event.getBlock()))
                  .putMap("block_state", mappers.mapBlockState(event.getExplodedBlockState()))
                  .putList(
                      MapperConstants.BLOCKS_KEY,
                      event.blockList().stream().map(mappers::mapBlock).toList())
                  .putString("yield", String.valueOf(event.getYield()))
                  .putString("explosion_result", event.getExplosionResult().name())
                  .build();

          return new PaperGameEvent(event.getEventName(), properties);
        });
  }
}
