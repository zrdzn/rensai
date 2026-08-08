package dev.rensai.agent.paper.mapper.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.rensai.agent.paper.mapper.MapperConstants;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.Test;

public class ItemStackMapperTest {

  @Test
  public void testMapItemStack() {
    ItemMetaMapper itemMetaMapper = new ItemMetaMapper();
    ItemStackMapper mapper = new ItemStackMapper(itemMetaMapper);
    ItemStack itemStack = mock(ItemStack.class);
    ItemMeta itemMeta = mock(ItemMeta.class);

    when(itemStack.getItemMeta()).thenReturn(itemMeta);
    when(itemStack.getType()).thenReturn(Material.DIAMOND_SWORD);
    when(itemStack.getMaxStackSize()).thenReturn(1);
    when(itemStack.getAmount()).thenReturn(1);

    Map<String, Object> result = mapper.map(itemStack);

    assertEquals("DIAMOND_SWORD", result.get(MapperConstants.TYPE_KEY));
    assertEquals(1, result.get("max_stack_size"));
    assertEquals(1, result.get(MapperConstants.AMOUNT_KEY));
    assertNotNull(result.get(MapperConstants.ITEM_META_KEY));
  }
}
