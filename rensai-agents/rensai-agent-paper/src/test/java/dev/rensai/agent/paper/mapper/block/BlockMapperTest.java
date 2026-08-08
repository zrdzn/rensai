package dev.rensai.agent.paper.mapper.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.rensai.agent.paper.mapper.MapperConstants;
import dev.rensai.agent.paper.mapper.WorldMapper;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.junit.jupiter.api.Test;

public class BlockMapperTest {

  @Test
  public void testMapBlock() {
    WorldMapper worldMapper = new WorldMapper();
    BlockMapper mapper = new BlockMapper(worldMapper);
    Block block = mock(Block.class);
    World world = mock(World.class);

    when(block.getType()).thenReturn(Material.DIAMOND_BLOCK);
    when(block.getWorld()).thenReturn(world);
    when(world.getName()).thenReturn("world");
    when(block.getX()).thenReturn(100);
    when(block.getY()).thenReturn(64);
    when(block.getZ()).thenReturn(-100);

    Map<String, Object> result = mapper.map(block);

    assertEquals("DIAMOND_BLOCK", result.get(MapperConstants.BLOCK_TYPE_KEY));
    assertEquals("100", result.get(MapperConstants.BLOCK_X_KEY));
    assertEquals("64", result.get(MapperConstants.BLOCK_Y_KEY));
    assertEquals("-100", result.get(MapperConstants.BLOCK_Z_KEY));
    assertNotNull(result.get(MapperConstants.WORLD_KEY));
  }
}
