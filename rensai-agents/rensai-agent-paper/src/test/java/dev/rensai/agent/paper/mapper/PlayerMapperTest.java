package dev.rensai.agent.paper.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

public class PlayerMapperTest {

  @Test
  public void testMapPlayer() {
    PlayerMapper mapper = new PlayerMapper();
    Player player = mock(Player.class);
    UUID uuid = UUID.randomUUID();

    when(player.getUniqueId()).thenReturn(uuid);
    when(player.getName()).thenReturn("Steve");

    Map<String, Object> result = mapper.map(player);

    assertEquals(uuid.toString(), result.get("player_uuid"));
    assertEquals("Steve", result.get("player_name"));
  }
}
