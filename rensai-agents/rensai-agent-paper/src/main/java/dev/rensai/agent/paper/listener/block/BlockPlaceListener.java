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

public class BlockPlaceListener extends AbstractAgentListener {

  public BlockPlaceListener(Plugin plugin, GrpcClient grpcClient) {
    super(plugin, grpcClient);
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
                  .putMap(MapperConstants.PLAYER_KEY, mappers.mapPlayer(event.getPlayer()))
                  .putMap(
                      MapperConstants.ITEM_IN_HAND_KEY, mappers.mapItemStack(event.getItemInHand()))
                  .putMap("replaced_state", mappers.mapBlockState(event.getBlockReplacedState()))
                  .putString("can_build", String.valueOf(event.canBuild()))
                  .putString("hand", event.getHand().name())
                  .build();

          return new PaperGameEvent(event.getEventName(), properties);
        });
  }
}
