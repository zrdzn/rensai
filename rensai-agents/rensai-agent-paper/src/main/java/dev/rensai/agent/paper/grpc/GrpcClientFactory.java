package dev.rensai.agent.paper.grpc;

import dev.rensai.grpc.IngestionServiceGrpc;
import io.grpc.ManagedChannel;

public final class GrpcClientFactory {
    private GrpcClientFactory() {
    }

    public static GrpcClient create(GrpcConfiguration config) {
        ManagedChannel channel = GrpcManagedChannelFactory.createChannel(config);
        IngestionServiceGrpc.IngestionServiceStub asyncStub = IngestionServiceGrpc.newStub(channel);
        return new GrpcClient(channel, asyncStub);
    }
}
