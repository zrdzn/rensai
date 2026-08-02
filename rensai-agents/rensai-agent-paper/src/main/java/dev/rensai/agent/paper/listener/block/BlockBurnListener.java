package dev.rensai.agent.paper.listener.block;

import dev.rensai.agent.common.grpc.GrpcClient;
import dev.rensai.agent.common.grpc.event.EventProperties;
import dev.rensai.agent.paper.PaperGameEvent;
import dev.rensai.agent.paper.listener.AbstractAgentListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.*;
import org.bukkit.plugin.Plugin;

public class BlockBurnListener extends AbstractAgentListener {

  public BlockBurnListener(Plugin plugin, GrpcClient grpcClient) {
    super(plugin, grpcClient);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockBurn(BlockBurnEvent event) {
    handleEvent(
        event,
        () -> {
          EventProperties properties =
              propertyBuilder()
                  .putMap("igniting_block", mappers.mapBlock(event.getIgnitingBlock()))
                  .build();

          return new PaperGameEvent(event.getEventName(), properties);
        });
  }
}
