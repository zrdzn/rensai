package dev.rensai.agent.paper.mapper;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockMapper implements Mapper<Block> {
  private final Mapper<World> worldMapper;

  public BlockMapper(Mapper<World> worldMapper) {
    this.worldMapper = worldMapper;
  }

  @Override
  public Map<String, Object> map(Block block) {
    if (block == null) {
      return new HashMap<>();
    }

    return Map.of(
        MapperConstants.BLOCK_TYPE_KEY, block.getType().name(),
        MapperConstants.WORLD_KEY, worldMapper.map(block.getWorld()),
        MapperConstants.BLOCK_X_KEY, String.valueOf(block.getX()),
        MapperConstants.BLOCK_Y_KEY, String.valueOf(block.getY()),
        MapperConstants.BLOCK_Z_KEY, String.valueOf(block.getZ()));
  }
}
