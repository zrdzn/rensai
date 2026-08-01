package dev.rensai.agent.paper.mapper;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.util.Vector;

public class VectorMapper implements Mapper<Vector> {
  @Override
  public Map<String, Object> map(Vector vector) {
    if (vector == null) {
      return new HashMap<>();
    }

    return Map.of(
        MapperConstants.X_KEY, vector.getX(),
        MapperConstants.Y_KEY, vector.getY(),
        MapperConstants.Z_KEY, vector.getZ(),
        MapperConstants.BLOCK_X_KEY, vector.getBlockX(),
        MapperConstants.BLOCK_Y_KEY, vector.getBlockY(),
        MapperConstants.BLOCK_Z_KEY, vector.getBlockZ());
  }
}
