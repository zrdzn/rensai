package dev.rensai.agent.paper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

public class EventCoverageScannerTest {

  @Test
  public void generateMissingEventsReport() {
    Reflections paperReflections = new Reflections("org.bukkit.event", "io.papermc.paper.event");
    Set<Class<? extends Event>> allEvents =
        paperReflections.getSubTypesOf(Event.class).stream()
            .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
            .collect(Collectors.toSet());

    Reflections agentReflections =
        new Reflections("dev.rensai.agent.paper.listener", Scanners.MethodsAnnotated);
    Set<Method> handlerMethods = agentReflections.getMethodsAnnotatedWith(EventHandler.class);

    Set<String> implementedEvents = new HashSet<>();

    for (Method method : handlerMethods) {
      if (method.getParameterCount() == 1) {
        Class<?> paramType = method.getParameterTypes()[0];
        if (Event.class.isAssignableFrom(paramType)) {
          implementedEvents.add(paramType.getSimpleName());
        }
      }
    }

    List<String> missingEvents =
        allEvents.stream()
            .map(Class::getSimpleName)
            .filter(name -> !implementedEvents.contains(name))
            .sorted()
            .toList();

    System.out.println(
        "Implemented events (" + implementedEvents.size() + "/" + allEvents.size() + "):");
    missingEvents.forEach(System.out::println);

    assertEquals(allEvents.size(), implementedEvents.size());
  }
}
