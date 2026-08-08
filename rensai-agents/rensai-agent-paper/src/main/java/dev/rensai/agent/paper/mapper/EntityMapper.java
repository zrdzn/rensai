package dev.rensai.agent.paper.mapper;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class EntityMapper implements Mapper<Entity> {
  private final Mapper<Location> locationMapper;

  public EntityMapper(Mapper<Location> locationMapper) {
    this.locationMapper = locationMapper;
  }

  @Override
  public Map<String, Object> map(Entity entity) {
    if (entity == null) {
      return new HashMap<>();
    }

    return Map.of(
        MapperConstants.ENTITY_ID, entity.getEntityId(),
        MapperConstants.LOCATION_KEY, locationMapper.map(entity.getLocation()),
        MapperConstants.ENTITY_TYPE, entity.getType().name());
  }
}
