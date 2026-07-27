package dev.rensai.agent.paper;

import dev.rensai.agent.paper.grpc.GrpcClient;
import dev.rensai.agent.paper.grpc.GrpcClientFactory;
import dev.rensai.agent.paper.grpc.GrpcConfiguration;
import dev.rensai.agent.paper.listener.BlockBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

public class RensaiAgentPlugin extends JavaPlugin {

    private GrpcClient grpcClient;

    @Override
    public void onEnable() {
        GrpcConfiguration config = new GrpcConfiguration("localhost", 9090);
        this.grpcClient = GrpcClientFactory.create(config);

        getServer().getPluginManager().registerEvents(new BlockBreakListener(this, grpcClient), this);
    }

    @Override
    public void onDisable() {
        if (this.grpcClient != null) {
            grpcClient.shutdown();
        }
    }
}
