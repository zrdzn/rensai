package dev.rensai.agent.paper;

import dev.rensai.agent.paper.listener.EventAvailabilityScanner;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.event.Event;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

public class EventCoverageScannerTest {

  @Test
  public void generateMissingEventsReport() throws IOException {
    Reflections paperReflections = new Reflections("org.bukkit.event", "io.papermc.paper.event");

    Set<Class<? extends Event>> allEventClasses =
        paperReflections.getSubTypesOf(Event.class).stream()
            .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
            .collect(Collectors.toSet());

    List<String> implementedEvents =
        EventAvailabilityScanner.scanImplementedEvents(RensaiAgentPlugin.LISTENER_PACKAGE_NAME);

    List<String> missingEvents =
        allEventClasses.stream()
            .map(Class::getSimpleName)
            .filter(name -> !implementedEvents.contains(name))
            .sorted()
            .toList();

    StringBuilder report = new StringBuilder();
    report.append("## Implemented Events (").append(implementedEvents.size()).append(")\n");
    implementedEvents.forEach(e -> report.append("- ").append(e).append("\n"));

    report.append("\n## Missing Events (").append(missingEvents.size()).append(")\n");
    missingEvents.forEach(e -> report.append("- ").append(e).append("\n"));

    Files.writeString(Path.of("event-coverage-report.md"), report.toString());
  }
}