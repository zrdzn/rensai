package dev.rensai.agent.paper.mapper;

import java.util.Map;
import org.bukkit.block.Block;

public class BlockMapper implements Mapper<Block> {
  @Override
  public Map<String, Object> map(Block block) {
    return Map.of(
        "block_type", block.getType().name(),
        "world_name", block.getWorld().getName(),
        "block_x", String.valueOf(block.getX()),
        "block_y", String.valueOf(block.getY()),
        "block_z", String.valueOf(block.getZ()));
  }
}
