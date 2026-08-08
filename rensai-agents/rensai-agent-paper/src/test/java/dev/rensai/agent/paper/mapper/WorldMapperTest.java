package dev.rensai.agent.paper.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.bukkit.World;
import org.junit.jupiter.api.Test;

public class WorldMapperTest {

  @Test
  public void testMapWorld() {
    WorldMapper mapper = new WorldMapper();
    World world = mock(World.class);

    when(world.getName()).thenReturn("world_the_end");

    Map<String, Object> result = mapper.map(world);

    assertEquals("world_the_end", result.get(MapperConstants.NAME_KEY));
  }
}
