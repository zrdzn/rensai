package dev.rensai.agent.paper.listener;

import dev.rensai.agent.common.grpc.GrpcClient;
import dev.rensai.agent.common.grpc.event.EventProperties;
import dev.rensai.agent.paper.PaperGameEvent;
import dev.rensai.agent.paper.mapper.MapperConstants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.*;
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
                  .putMap(MapperConstants.BLOCK_KEY, mappers.mapBlock(event.getBlock()))
                  .putMap(MapperConstants.PLAYER_KEY, mappers.mapPlayer(event.getPlayer()))
                  .putString("exp_to_drop", String.valueOf(event.getExpToDrop()))
                  .putString("is_drop_items", String.valueOf(event.isDropItems()))
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

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockDamage(BlockDamageEvent event) {
    handleEvent(
        event,
        () -> {
          EventProperties properties =
              propertyBuilder()
                  .putMap(MapperConstants.BLOCK_KEY, mappers.mapBlock(event.getBlock()))
                  .putMap(MapperConstants.PLAYER_KEY, mappers.mapPlayer(event.getPlayer()))
                  .putMap(
                      MapperConstants.ITEM_IN_HAND_KEY, mappers.mapItemStack(event.getItemInHand()))
                  .putString("block_face", event.getBlockFace().name())
                  .build();

          return new PaperGameEvent(event.getEventName(), properties);
        });
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockExp(BlockExpEvent event) {
    handleEvent(
        event,
        () -> {
          EventProperties properties =
              propertyBuilder()
                  .putMap(MapperConstants.BLOCK_KEY, mappers.mapBlock(event.getBlock()))
                  .putString("exp_to_drop", String.valueOf(event.getExpToDrop()))
                  .build();

          return new PaperGameEvent(event.getEventName(), properties);
        });
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockDispense(BlockDispenseEvent event) {
    handleEvent(
        event,
        () -> {
          EventProperties properties =
              propertyBuilder()
                  .putMap(MapperConstants.BLOCK_KEY, mappers.mapBlock(event.getBlock()))
                  .putMap(MapperConstants.ITEM_KEY, mappers.mapItemStack(event.getItem()))
                  .putMap(MapperConstants.VELOCITY_KEY, mappers.mapVector(event.getVelocity()))
                  .build();

          return new PaperGameEvent(event.getEventName(), properties);
        });
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

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockFade(BlockFadeEvent event) {
    handleEvent(
        event,
        () -> {
          EventProperties properties =
              propertyBuilder()
                  .putMap(MapperConstants.BLOCK_KEY, mappers.mapBlock(event.getBlock()))
                  .putMap("new_state", mappers.mapBlockState(event.getNewState()))
                  .build();

          return new PaperGameEvent(event.getEventName(), properties);
        });
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
