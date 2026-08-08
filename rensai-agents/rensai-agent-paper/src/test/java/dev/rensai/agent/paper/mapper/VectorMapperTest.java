package dev.rensai.agent.paper.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.Test;

public class VectorMapperTest {

  @Test
  public void testMapVector() {
    VectorMapper mapper = new VectorMapper();
    Vector vector = mock(Vector.class);

    when(vector.getX()).thenReturn(1.1);
    when(vector.getY()).thenReturn(2.2);
    when(vector.getZ()).thenReturn(3.3);
    when(vector.getBlockX()).thenReturn(1);
    when(vector.getBlockY()).thenReturn(2);
    when(vector.getBlockZ()).thenReturn(3);

    Map<String, Object> result = mapper.map(vector);

    assertEquals(1.1, result.get(MapperConstants.X_KEY));
    assertEquals(2.2, result.get(MapperConstants.Y_KEY));
    assertEquals(3.3, result.get(MapperConstants.Z_KEY));
    assertEquals(1, result.get(MapperConstants.BLOCK_X_KEY));
    assertEquals(2, result.get(MapperConstants.BLOCK_Y_KEY));
    assertEquals(3, result.get(MapperConstants.BLOCK_Z_KEY));
  }
}
