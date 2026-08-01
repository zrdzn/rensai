package dev.rensai.agent.paper.mapper;

import java.util.Map;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MapperRegistry {

  private final Mapper<Block> blockMapper;
  private final Mapper<Player> playerMapper;
  private final Mapper<ItemMeta> itemMetaMapper;
  private final Mapper<ItemStack> itemStackMapper;

  public MapperRegistry() {
    this.blockMapper = new BlockMapper();
    this.playerMapper = new PlayerMapper();
    this.itemMetaMapper = new ItemMetaMapper();
    this.itemStackMapper = new ItemStackMapper(itemMetaMapper);
  }

  public Map<String, Object> mapBlock(Block block) {
    return blockMapper.map(block);
  }

  public Map<String, Object> mapPlayer(Player player) {
    return playerMapper.map(player);
  }

  public Map<String, Object> mapItemMeta(ItemMeta itemMeta) {
    return itemMetaMapper.map(itemMeta);
  }

  public Map<String, Object> mapItemStack(ItemStack itemStack) {
    return itemStackMapper.map(itemStack);
  }
}
