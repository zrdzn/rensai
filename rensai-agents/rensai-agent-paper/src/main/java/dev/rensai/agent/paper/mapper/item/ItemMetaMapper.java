package dev.rensai.agent.paper.mapper.item;

import dev.rensai.agent.paper.mapper.Mapper;
import dev.rensai.agent.paper.mapper.MapperConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemMetaMapper implements Mapper<ItemMeta> {
  private static final PlainTextComponentSerializer SERIALIZER =
      PlainTextComponentSerializer.plainText();

  @Override
  public Map<String, Object> map(ItemMeta itemMeta) {
    if (itemMeta == null) {
      return new HashMap<>();
    }

    Map<String, Object> properties = new HashMap<>();

    // 1. display_name
    Component displayName = itemMeta.displayName();
    if (itemMeta.hasDisplayName() && displayName != null) {
      properties.put(MapperConstants.DISPLAY_NAME_KEY, SERIALIZER.serialize(displayName));
    }

    // 2. lore
    List<Component> lore = itemMeta.lore();
    if (itemMeta.hasLore() && lore != null) {
      String plainLore = lore.stream().map(SERIALIZER::serialize).collect(Collectors.joining("\n"));
      properties.put(MapperConstants.LORE_KEY, plainLore);
    }

    // 3. enchants
    Map<Enchantment, Integer> enchants = itemMeta.getEnchants();
    if (!enchants.isEmpty()) {
      Map<String, Integer> parsedEnchants =
          enchants.entrySet().stream()
              .collect(
                  Collectors.toMap(entry -> entry.getKey().getKey().getKey(), Map.Entry::getValue));

      properties.put("enchants", parsedEnchants);
    }

    return properties;
  }
}
