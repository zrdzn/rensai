package dev.rensai.agent.paper.mapper;

import java.util.Map;
import org.bukkit.entity.Player;

public class PlayerMapper implements Mapper<Player> {
  @Override
  public Map<String, Object> map(Player player) {
    return Map.of(
        "player_uuid", player.getUniqueId().toString(),
        "player_name", player.getName());
  }
}
