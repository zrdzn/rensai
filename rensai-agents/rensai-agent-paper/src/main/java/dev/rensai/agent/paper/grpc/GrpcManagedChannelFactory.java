package dev.rensai.agent.paper.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public final class GrpcManagedChannelFactory {
    private GrpcManagedChannelFactory() {
    }

    public static ManagedChannel createChannel(GrpcConfiguration config) {
        return ManagedChannelBuilder.forAddress(config.host(), config.port())
                // TODO tls
                .usePlaintext()
                .build();
    }
}
