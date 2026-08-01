package dev.rensai.agent.paper.mapper;

import java.util.Map;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class MapperRegistry {

  private final Mapper<Block> blockMapper;
  private final Mapper<Player> playerMapper;

  public MapperRegistry() {
    this.blockMapper = new BlockMapper();
    this.playerMapper = new PlayerMapper();
  }

  public Map<String, Object> mapBlock(Block block) {
    return blockMapper.map(block);
  }

  public Map<String, Object> mapPlayer(Player player) {
    return playerMapper.map(player);
  }
}
