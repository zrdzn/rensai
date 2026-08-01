package dev.rensai.agent.paper.mapper;

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
    return Map.of(
        "item_meta", itemMetaMapper.map(itemStack.getItemMeta()),
        "type", itemStack.getType().name(),
        "max_stack_size", itemStack.getMaxStackSize(),
        "amount", itemStack.getAmount());
  }
}
