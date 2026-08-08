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
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.junit.jupiter.api.Test;

public class BlockStateMapperTest {

  @Test
  public void testMapBlockState() {
    WorldMapper worldMapper = new WorldMapper();
    BlockStateMapper mapper = new BlockStateMapper(worldMapper);
    BlockState blockState = mock(BlockState.class);
    World world = mock(World.class);
    BlockData blockData = mock(BlockData.class);

    when(blockState.getType()).thenReturn(Material.OAK_LOG);
    when(blockState.getWorld()).thenReturn(world);
    when(world.getName()).thenReturn("world");
    when(blockState.getX()).thenReturn(50);
    when(blockState.getY()).thenReturn(70);
    when(blockState.getZ()).thenReturn(50);
    when(blockState.getLightLevel()).thenReturn((byte) 15);
    when(blockState.isPlaced()).thenReturn(true);
    when(blockState.isCollidable()).thenReturn(true);
    when(blockState.isSuffocating()).thenReturn(false);
    when(blockState.getBlockData()).thenReturn(blockData);
    when(blockData.getAsString()).thenReturn("minecraft:oak_log[axis=y]");

    Map<String, Object> result = mapper.map(blockState);

    assertEquals("OAK_LOG", result.get(MapperConstants.TYPE_KEY));
    assertEquals(50, result.get(MapperConstants.X_KEY));
    assertEquals(70, result.get(MapperConstants.Y_KEY));
    assertEquals(50, result.get(MapperConstants.Z_KEY));
    assertEquals((byte) 15, result.get("light_level"));
    assertEquals(true, result.get("is_placed"));
    assertEquals(true, result.get("is_collidable"));
    assertEquals(false, result.get("is_suffocating"));
    assertEquals("minecraft:oak_log[axis=y]", result.get("block_data"));
    assertNotNull(result.get(MapperConstants.WORLD_KEY));
  }
}
