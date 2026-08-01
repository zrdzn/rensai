package dev.rensai.agent.paper.mapper;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.World;

public class WorldMapper implements Mapper<World> {

  @Override
  public Map<String, Object> map(World world) {
    if (world == null) {
      return new HashMap<>();
    }

    return Map.of(MapperConstants.NAME_KEY, world.getName());
  }
}
