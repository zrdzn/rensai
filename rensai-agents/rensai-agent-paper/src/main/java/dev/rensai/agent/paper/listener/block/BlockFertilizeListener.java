package dev.rensai.agent.paper.listener.block;

import dev.rensai.agent.common.grpc.GrpcClient;
import dev.rensai.agent.common.grpc.event.EventProperties;
import dev.rensai.agent.paper.PaperGameEvent;
import dev.rensai.agent.paper.listener.AbstractAgentListener;
import dev.rensai.agent.paper.mapper.MapperConstants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.plugin.Plugin;

public class BlockFertilizeListener extends AbstractAgentListener {

  public BlockFertilizeListener(Plugin plugin, GrpcClient grpcClient) {
    super(plugin, grpcClient);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockFertilize(BlockFertilizeEvent event) {
    handleEvent(
        event,
        () -> {
          EventProperties properties =
              propertyBuilder()
                  .putMap(MapperConstants.BLOCK_KEY, mappers.mapBlock(event.getBlock()))
                  .putList(
                      MapperConstants.BLOCKS_KEY,
                      event.getBlocks().stream().map(mappers::mapBlockState).toList())
                  .build();

          return new PaperGameEvent(event.getEventName(), properties);
        });
  }
}
