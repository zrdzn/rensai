package dev.rensai.agent.paper.listener;

import dev.rensai.agent.common.grpc.GrpcClient;
import dev.rensai.agent.common.grpc.event.EventProperties;
import dev.rensai.agent.paper.PaperGameEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

public class BlockListener extends AbstractAgentListener {

  public BlockListener(Plugin plugin, GrpcClient grpcClient) {
    super(plugin, grpcClient);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockBreak(BlockBreakEvent event) {
    handleEvent(
        event,
        () -> {
          EventProperties properties =
              propertyBuilder()
                  .putMap("block", mappers.mapBlock(event.getBlock()))
                  .putMap("player", mappers.mapPlayer(event.getPlayer()))
                  .put("exp_to_drop", String.valueOf(event.getExpToDrop()))
                  .put("is_drop_items", String.valueOf(event.isDropItems()))
                  .build();

          return new PaperGameEvent(event.getEventName(), properties);
        });
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockPlace(BlockPlaceEvent event) {
    handleEvent(
        event,
        () -> {
          EventProperties properties =
              propertyBuilder()
                  .putMap("block_placed", mappers.mapBlock(event.getBlockPlaced()))
                  .putMap("block_against", mappers.mapBlock(event.getBlockAgainst()))
                  .putMap("player", mappers.mapPlayer(event.getPlayer()))
                  .putMap("item_in_hand", mappers.mapItemStack(event.getItemInHand()))
                  .put("can_build", String.valueOf(event.canBuild()))
                  .put("hand", event.getHand().name())
                  .build();

          return new PaperGameEvent(event.getEventName(), properties);
        });
  }
}
