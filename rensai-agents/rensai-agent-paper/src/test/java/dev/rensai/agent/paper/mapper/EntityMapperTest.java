package dev.rensai.agent.paper.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;

public class EntityMapperTest {

  @Test
  public void testMapEntity() {
    LocationMapper locationMapper = new LocationMapper();
    EntityMapper mapper = new EntityMapper(locationMapper);
    Entity entity = mock(Entity.class);
    Location location = mock(Location.class);

    when(entity.getEntityId()).thenReturn(1001);
    when(entity.getType()).thenReturn(EntityType.ZOMBIE);
    when(entity.getLocation()).thenReturn(location);

    when(location.getX()).thenReturn(1.0);
    when(location.getY()).thenReturn(2.0);
    when(location.getZ()).thenReturn(3.0);
    when(location.getPitch()).thenReturn(0.0f);
    when(location.getYaw()).thenReturn(0.0f);

    Map<String, Object> result = mapper.map(entity);

    assertEquals(1001, result.get(MapperConstants.ENTITY_ID));
    assertEquals("ZOMBIE", result.get(MapperConstants.ENTITY_TYPE));
    assertNotNull(result.get(MapperConstants.LOCATION_KEY));
  }
}
