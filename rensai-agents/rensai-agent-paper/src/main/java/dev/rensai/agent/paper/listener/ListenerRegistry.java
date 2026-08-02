package dev.rensai.agent.paper.listener;

import dev.rensai.agent.common.grpc.GrpcClient;
import dev.rensai.agent.paper.RensaiAgentPlugin;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

public class ListenerRegistry {

  private final Map<String, Class<? extends Listener>> listeners = new HashMap<>();

  @SuppressWarnings("unchecked")
  public void scanAndMapListeners(String packageName) {
    Reflections reflections = new Reflections(packageName, Scanners.MethodsAnnotated);
    Set<Method> handlerMethods = reflections.getMethodsAnnotatedWith(EventHandler.class);

    for (Method method : handlerMethods) {
      if (method.getParameterCount() == 1) {
        Class<?> paramType = method.getParameterTypes()[0];
        if (Event.class.isAssignableFrom(paramType)) {
          String eventName = paramType.getSimpleName();
          Class<? extends Listener> listenerClass =
              (Class<? extends Listener>) method.getDeclaringClass();
          listeners.put(eventName, listenerClass);
        }
      }
    }
  }

  public void registerActiveEvents(
      List<String> activeEvents, RensaiAgentPlugin plugin, GrpcClient client) {
    for (String eventName : activeEvents) {
      Class<? extends Listener> listenerClass = listeners.get(eventName);

      if (listenerClass != null) {
        try {
          Listener listener =
              listenerClass
                  .getDeclaredConstructor(RensaiAgentPlugin.class, GrpcClient.class)
                  .newInstance(plugin, client);

          plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
