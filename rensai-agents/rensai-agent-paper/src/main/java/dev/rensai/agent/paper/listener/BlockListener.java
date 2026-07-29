package dev.rensai.agent.paper.listener;

import dev.rensai.agent.common.grpc.GrpcClient;
import dev.rensai.agent.paper.PaperGameEvent;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

public class BlockListener extends AbstractAgentListener {

  public BlockListener(Plugin plugin, GrpcClient grpcClient) {
    super(plugin, grpcClient);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockBreak(BlockBreakEvent event) {
    handleEvent(
        event,
        e -> {
          Player player = event.getPlayer();
          Block block = event.getBlock();

          Map<String, String> properties = new HashMap<>();
          properties.put("player_uuid", player.getUniqueId().toString());
          properties.put("player_name", player.getName());
          properties.put("block_type", block.getType().name());
          properties.put("world_name", block.getWorld().getName());
          properties.put("block_x", String.valueOf(block.getX()));
          properties.put("block_y", String.valueOf(block.getY()));
          properties.put("block_z", String.valueOf(block.getZ()));
          properties.put("exp_to_drop", String.valueOf(event.getExpToDrop()));
          properties.put("is_drop_items", String.valueOf(event.isDropItems()));

          return new PaperGameEvent(e.getEventName(), properties);
        });
  }
}
