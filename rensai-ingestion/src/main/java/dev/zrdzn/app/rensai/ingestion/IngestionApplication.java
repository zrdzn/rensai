package dev.zrdzn.app.rensai.ingestion;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class IngestionApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(IngestionApplication.class);
  private static final int AWAIT_TERMINATION_SECONDS = 30;

  private final Object lock = new Object();

  private volatile Server server;

  void start(int port) {
    synchronized (lock) {
      if (server != null) {
        LOGGER.error("Server was already initialized.");
        return;
      }

      server = ServerBuilder.forPort(port).addService(new IngestionGrpcEventListener()).build();

      try {
        server.start();
      } catch (IOException ex) {
        LOGGER.error("Error while starting application.", ex);
        stop();
      }
    }

    Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
  }

  void stop() {
    synchronized (lock) {
      if (server == null) {
        LOGGER.error("Server instance is not initialized - cannot shutdown.");
        return;
      }

      server.shutdown();
      try {
        server.awaitTermination(AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
      }

      server = null;
      LOGGER.info("Server gracefully stopped.");
    }
  }

  void await() {
    try {
      if (server == null) {
        LOGGER.error("Cannot await for server termination.");
        return;
      }

      server.awaitTermination();
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
      LOGGER.error("Server execution was interrupted.", ex);
    }
  }
}
