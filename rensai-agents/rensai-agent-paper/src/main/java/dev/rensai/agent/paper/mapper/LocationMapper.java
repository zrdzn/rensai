package dev.rensai.agent.paper.mapper;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;

public class LocationMapper implements Mapper<Location> {
  @Override
  public Map<String, Object> map(Location location) {
    if (location == null) {
      return new HashMap<>();
    }

    return Map.of(
        MapperConstants.X_KEY, location.getX(),
        MapperConstants.Y_KEY, location.getY(),
        MapperConstants.Z_KEY, location.getZ(),
        MapperConstants.PITCH_KEY, location.getPitch(),
        MapperConstants.YAW_KEY, location.getYaw());
  }
}
