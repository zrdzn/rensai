package dev.zrdzn.app.rensai.ingestion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IngestionBootstrap {

  private static final Logger LOGGER = LoggerFactory.getLogger(IngestionBootstrap.class);
  private static final int DEFAULT_PORT = 9292;

  public static void main(String[] args) {
    int port = DEFAULT_PORT;
    if (args.length != 0) {
      try {
        port = Integer.parseInt(args[0]);
      } catch (NumberFormatException ex) {
        LOGGER.error("Provided server port is not an integer.", ex);
      }
    }

    LOGGER.info("Using {} server port.", port);

    IngestionApplication application = new IngestionApplication();
    application.start(port);
    application.await();
  }
}
