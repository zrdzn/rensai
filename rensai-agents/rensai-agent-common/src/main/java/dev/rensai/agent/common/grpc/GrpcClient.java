package dev.rensai.agent.common.grpc;

import dev.rensai.grpc.EventResponse;
import dev.rensai.grpc.GenericEventRequest;
import dev.rensai.grpc.IngestionServiceGrpc;
import io.grpc.ManagedChannel;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(GrpcClient.class);

  private final ManagedChannel channel;
  private final IngestionServiceGrpc.IngestionServiceStub asyncStub;

  public GrpcClient(ManagedChannel channel, IngestionServiceGrpc.IngestionServiceStub asyncStub) {
    this.channel = channel;
    this.asyncStub = asyncStub;
  }

  public void sendGenericEvent(GenericEventRequest request) {
    asyncStub.sendGenericEvent(
        request,
        new io.grpc.stub.StreamObserver<>() {
          @Override
          public void onNext(EventResponse response) {
            // noop
          }

          @Override
          public void onError(Throwable t) {
            LOGGER.error("Failed to send gRPC event: {}", t.getMessage());
          }

          @Override
          public void onCompleted() {
            // noop
          }
        });
  }

  public void shutdown() {
    try {
      if (channel != null && !channel.isShutdown()) {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.warn("gRPC channel shutdown was interrupted.");
    }
  }
}
