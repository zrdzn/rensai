package dev.zrdzn.app.rensai.ingestion;

import dev.rensai.grpc.EventResponse;
import dev.rensai.grpc.GenericEventRequest;
import dev.rensai.grpc.IngestionServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IngestionGrpcEventListener extends IngestionServiceGrpc.IngestionServiceImplBase {
  private static final Logger LOGGER = LoggerFactory.getLogger(IngestionGrpcEventListener.class);

  @Override
  public void sendGenericEvent(
      GenericEventRequest request, StreamObserver<EventResponse> responseObserver) {
    EventResponse response = EventResponse.newBuilder().setSuccess(true).build();
    LOGGER.error("Received request: {}, {}", request.getEventName(), request.getGameSource());
    LOGGER.error("Data:");
    request
        .getProperties()
        .getFieldsMap()
        .forEach((k, v) -> LOGGER.error("Key: {}, Value: {}", k, v));
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
