package dev.rensai.agent.paper;

import dev.rensai.agent.common.grpc.GrpcClient;
import dev.rensai.agent.common.grpc.GrpcClientFactory;
import dev.rensai.agent.common.grpc.GrpcConfiguration;
import dev.rensai.agent.common.grpc.event.SupportedEvents;
import dev.rensai.agent.paper.listener.EventAvailabilityScanner;
import dev.rensai.agent.paper.listener.ListenerRegistry;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;

public class RensaiAgentPlugin extends JavaPlugin {

  public static final String LISTENER_PACKAGE_NAME = "dev.rensai.agent.paper.listener";

  private GrpcClient grpcClient;

  @Override
  public void onEnable() {
    GrpcConfiguration config = new GrpcConfiguration("localhost", 9292);
    this.grpcClient = GrpcClientFactory.create(config);

    ListenerRegistry listenerRegistry = new ListenerRegistry();
    listenerRegistry.scanAndMapListeners(LISTENER_PACKAGE_NAME);

    // TODO it will be replaced by call to database
    List<String> activeEvents = List.of("BlockBreakEvent", "BlockPlaceEvent");
    listenerRegistry.registerActiveEvents(activeEvents, this, grpcClient);

    List<String> eventNames = EventAvailabilityScanner.scanImplementedEvents(LISTENER_PACKAGE_NAME);
    String agentId = "paper-node-1";
    grpcClient.reportSupportedEvents(new SupportedEvents(agentId, "PAPER", eventNames));
  }

  @Override
  public void onDisable() {
    if (this.grpcClient != null) {
      grpcClient.shutdown();
    }
  }
}
