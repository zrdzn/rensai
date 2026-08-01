package dev.rensai.agent.paper.mapper;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.World;
import org.bukkit.block.BlockState;

public class BlockStateMapper implements Mapper<BlockState> {

  private final Mapper<World> worldMapper;

  public BlockStateMapper(Mapper<World> worldMapper) {
    this.worldMapper = worldMapper;
  }

  @Override
  public Map<String, Object> map(BlockState blockState) {
    if (blockState == null) {
      return new HashMap<>();
    }

    Map<String, Object> properties = new HashMap<>();

    properties.put(MapperConstants.TYPE_KEY, blockState.getType().name());
    properties.put(MapperConstants.WORLD_KEY, worldMapper.map(blockState.getWorld()));
    properties.put(MapperConstants.X_KEY, blockState.getX());
    properties.put(MapperConstants.Y_KEY, blockState.getY());
    properties.put(MapperConstants.Z_KEY, blockState.getZ());

    properties.put("light_level", blockState.getLightLevel());
    properties.put("is_placed", blockState.isPlaced());
    properties.put("is_collidable", blockState.isCollidable());
    properties.put("is_suffocating", blockState.isSuffocating());

    properties.put("block_data", blockState.getBlockData().getAsString());

    return properties;
  }
}
