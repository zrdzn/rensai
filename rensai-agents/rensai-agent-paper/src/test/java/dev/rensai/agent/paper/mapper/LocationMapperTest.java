package dev.rensai.agent.paper.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.bukkit.Location;
import org.junit.jupiter.api.Test;

public class LocationMapperTest {

  @Test
  public void testMapLocation() {
    LocationMapper mapper = new LocationMapper();
    Location location = mock(Location.class);

    when(location.getX()).thenReturn(10.5);
    when(location.getY()).thenReturn(20.5);
    when(location.getZ()).thenReturn(30.5);
    when(location.getPitch()).thenReturn(45.0f);
    when(location.getYaw()).thenReturn(90.0f);

    Map<String, Object> result = mapper.map(location);

    assertEquals(10.5, result.get(MapperConstants.X_KEY));
    assertEquals(20.5, result.get(MapperConstants.Y_KEY));
    assertEquals(30.5, result.get(MapperConstants.Z_KEY));
    assertEquals(45.0f, result.get(MapperConstants.PITCH_KEY));
    assertEquals(90.0f, result.get(MapperConstants.YAW_KEY));
  }
}
