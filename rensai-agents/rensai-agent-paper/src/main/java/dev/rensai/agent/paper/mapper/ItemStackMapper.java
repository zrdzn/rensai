package dev.rensai.agent.paper.mapper;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackMapper implements Mapper<ItemStack> {

  private final Mapper<ItemMeta> itemMetaMapper;

  public ItemStackMapper(Mapper<ItemMeta> itemMetaMapper) {
    this.itemMetaMapper = itemMetaMapper;
  }

  @Override
  public Map<String, Object> map(ItemStack itemStack) {
    if (itemStack == null) {
      return new HashMap<>();
    }

    return Map.of(
        MapperConstants.ITEM_META_KEY,
        itemMetaMapper.map(itemStack.getItemMeta()),
        MapperConstants.TYPE_KEY,
        itemStack.getType().name(),
        "max_stack_size",
        itemStack.getMaxStackSize(),
        MapperConstants.AMOUNT_KEY,
        itemStack.getAmount());
  }
}
