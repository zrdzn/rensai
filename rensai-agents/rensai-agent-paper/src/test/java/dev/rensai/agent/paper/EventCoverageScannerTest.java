package dev.rensai.agent.paper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.event.Event;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

public class EventCoverageScannerTest {

  @Test
  public void generateMissingEventsReport() {
    Reflections paperReflections = new Reflections("org.bukkit.event", "io.papermc.paper.event");

    Set<Class<? extends Event>> allEventClasses =
        paperReflections.getSubTypesOf(Event.class).stream()
            .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
            .collect(Collectors.toSet());

    List<String> implementedEvents = EventAvailabilityScanner.scanImplementedEvents();

    List<String> missingEvents =
        allEventClasses.stream()
            .map(Class::getSimpleName)
            .filter(name -> !implementedEvents.contains(name))
            .sorted()
            .toList();

    System.out.println("Implemented events (" + implementedEvents.size() + "):");
    implementedEvents.forEach(System.out::println);

    System.out.println();
    System.out.println("Missing implemented events (" + missingEvents.size() + "):");
    missingEvents.forEach(System.out::println);

    assertEquals(allEventClasses.size(), implementedEvents.size());
  }
}
