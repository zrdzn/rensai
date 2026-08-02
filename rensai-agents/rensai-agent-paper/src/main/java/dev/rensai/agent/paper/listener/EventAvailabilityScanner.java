package dev.rensai.agent.paper.listener;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

public final class EventAvailabilityScanner {

  private EventAvailabilityScanner() {}

  public static List<String> scanImplementedEvents(String packageName) {
    Reflections agentReflections = new Reflections(packageName, Scanners.MethodsAnnotated);
    Set<Method> handlerMethods = agentReflections.getMethodsAnnotatedWith(EventHandler.class);

    return handlerMethods.stream()
        .filter(method -> method.getParameterCount() == 1)
        .map(method -> method.getParameterTypes()[0])
        .filter(Event.class::isAssignableFrom)
        .map(Class::getSimpleName)
        .distinct()
        .sorted()
        .collect(Collectors.toList());
  }
}
