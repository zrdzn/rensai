package dev.rensai.agent.paper.listener;

import dev.rensai.agent.paper.grpc.GrpcClient;
import dev.rensai.grpc.GenericEventRequest;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class BlockBreakListener implements Listener {

    private final Plugin plugin;
    private final GrpcClient grpcClient;

    public BlockBreakListener(Plugin plugin, GrpcClient grpcClient) {
        this.plugin = plugin;
        this.grpcClient = grpcClient;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        Map<String, String> properties = new HashMap<>();
        properties.put("player_uuid", player.getUniqueId().toString());
        properties.put("player_name", player.getName());
        properties.put("block_type", block.getType().name());
        properties.put("world_name", block.getWorld().getName());
        properties.put("block_x", String.valueOf(block.getX()));
        properties.put("block_y", String.valueOf(block.getY()));
        properties.put("block_z", String.valueOf(block.getZ()));
        properties.put("exp_to_drop", String.valueOf(event.getExpToDrop()));
        properties.put("is_drop_items", String.valueOf(event.isDropItems()));

        GenericEventRequest request = GenericEventRequest.newBuilder()
                .setEventName(event.getEventName())
                .setGameSource("PAPER")
                .setTimestamp(System.currentTimeMillis())
                .putAllEventData(properties)
                .build();

        plugin.getServer().getAsyncScheduler().runNow(plugin, _ -> grpcClient.sendGenericEvent(request));
    }
}