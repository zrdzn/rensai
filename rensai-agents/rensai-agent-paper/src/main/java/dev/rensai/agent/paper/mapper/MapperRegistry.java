package dev.rensai.agent.paper.mapper;

import java.util.Map;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class MapperRegistry {

  private final Mapper<Player> playerMapper;
  private final Mapper<ItemMeta> itemMetaMapper;
  private final Mapper<ItemStack> itemStackMapper;
  private final Mapper<Vector> vectorMapper;
  private final Mapper<World> worldMapper;
  private final Mapper<BlockState> blockStateMapper;
  private final Mapper<Block> blockMapper;

  public MapperRegistry() {
    this.playerMapper = new PlayerMapper();
    this.itemMetaMapper = new ItemMetaMapper();
    this.itemStackMapper = new ItemStackMapper(itemMetaMapper);
    this.vectorMapper = new VectorMapper();
    this.worldMapper = new WorldMapper();
    this.blockStateMapper = new BlockStateMapper(worldMapper);
    this.blockMapper = new BlockMapper(worldMapper);
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

  public Map<String, Object> mapVector(Vector vector) {
    return vectorMapper.map(vector);
  }

  public Map<String, Object> mapWorld(World world) {
    return worldMapper.map(world);
  }

  public Map<String, Object> mapBlockState(BlockState blockState) {
    return blockStateMapper.map(blockState);
  }

  public Map<String, Object> mapBlock(Block block) {
    return blockMapper.map(block);
  }
}
