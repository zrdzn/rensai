package dev.rensai.agent.paper.mapper.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.rensai.agent.paper.mapper.MapperConstants;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.Test;

public class ItemMetaMapperTest {

  @Test
  public void testMapItemMeta() {
    ItemMetaMapper mapper = new ItemMetaMapper();
    ItemMeta itemMeta = mock(ItemMeta.class);

    Component displayName = Component.text("Epic Sword");
    Component loreLine1 = Component.text("A very epic sword");
    Component loreLine2 = Component.text("It glows in the dark");

    when(itemMeta.hasDisplayName()).thenReturn(true);
    when(itemMeta.displayName()).thenReturn(displayName);
    when(itemMeta.hasLore()).thenReturn(true);
    when(itemMeta.lore()).thenReturn(List.of(loreLine1, loreLine2));
    when(itemMeta.getEnchants()).thenReturn(Collections.emptyMap());

    Map<String, Object> result = mapper.map(itemMeta);

    assertEquals("Epic Sword", result.get(MapperConstants.DISPLAY_NAME_KEY));
    assertEquals("A very epic sword\nIt glows in the dark", result.get(MapperConstants.LORE_KEY));
    assertFalse(result.containsKey("enchants"));
  }
}
