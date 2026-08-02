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

public class BlockCookListener extends AbstractAgentListener {

  public BlockCookListener(Plugin plugin, GrpcClient grpcClient) {
    super(plugin, grpcClient);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockCook(BlockCookEvent event) {
    handleEvent(
        event,
        () -> {
          EventProperties properties =
              propertyBuilder()
                  .putMap(MapperConstants.BLOCK_KEY, mappers.mapBlock(event.getBlock()))
                  .putMap("source", mappers.mapItemStack(event.getSource()))
                  .putMap("result", mappers.mapItemStack(event.getResult()))
                  .build();

          return new PaperGameEvent(event.getEventName(), properties);
        });
  }
}
