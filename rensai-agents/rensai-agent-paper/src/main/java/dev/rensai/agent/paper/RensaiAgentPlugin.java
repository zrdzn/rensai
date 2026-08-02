package dev.rensai.agent.paper;

import dev.rensai.agent.common.grpc.GrpcClient;
import dev.rensai.agent.common.grpc.GrpcClientFactory;
import dev.rensai.agent.common.grpc.GrpcConfiguration;
import dev.rensai.agent.common.grpc.event.SupportedEvents;
import dev.rensai.agent.paper.listener.BlockListener;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;

public class RensaiAgentPlugin extends JavaPlugin {

  private GrpcClient grpcClient;

  @Override
  public void onEnable() {
    GrpcConfiguration config = new GrpcConfiguration("localhost", 9292);
    this.grpcClient = GrpcClientFactory.create(config);

    getServer().getPluginManager().registerEvents(new BlockListener(this, grpcClient), this);

    List<String> eventNames = EventAvailabilityScanner.scanImplementedEvents();
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
