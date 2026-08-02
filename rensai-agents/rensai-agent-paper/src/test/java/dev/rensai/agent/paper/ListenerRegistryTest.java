package dev.rensai.agent.paper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.rensai.agent.paper.listener.ListenerRegistry;
import java.lang.reflect.Field;
import java.util.Map;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListenerRegistryTest {

  private ListenerRegistry registry;

  @BeforeEach
  public void setUp() {
    registry = new ListenerRegistry();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testScanAndMapListeners() throws Exception {
    registry.scanAndMapListeners("dev.rensai.agent.paper");

    Field mapField = ListenerRegistry.class.getDeclaredField("listeners");
    mapField.setAccessible(true);
    Map<String, Class<? extends Listener>> listenerMap =
        (Map<String, Class<? extends Listener>>) mapField.get(registry);

    assertNotNull(listenerMap);

    assertTrue(listenerMap.containsKey("MockValidEvent"));
    assertEquals(MockValidListener.class, listenerMap.get("MockValidEvent"));

    assertTrue(listenerMap.containsKey("MockSecondaryEvent"));
    assertEquals(MockSecondaryListener.class, listenerMap.get("MockSecondaryEvent"));

    assertFalse(listenerMap.containsKey("String"));
  }

  public static class MockValidEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
      return handlers;
    }

    public static HandlerList getHandlerList() {
      return handlers;
    }
  }

  public static class MockSecondaryEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
      return handlers;
    }

    public static HandlerList getHandlerList() {
      return handlers;
    }
  }

  public static class MockValidListener implements Listener {
    @EventHandler
    public void onValidEvent(MockValidEvent event) {}

    @EventHandler
    public void onInvalidMultipleParams(MockValidEvent event, String extraParam) {}

    @EventHandler
    public void onInvalidNoParams() {}

    @EventHandler
    public void onInvalidWrongParamType(String notAnEvent) {}
  }

  public static class MockSecondaryListener implements Listener {
    @EventHandler
    public void onSecondaryEvent(MockSecondaryEvent event) {}
  }
}
