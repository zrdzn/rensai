package dev.rensai.agent.paper.grpc;

public record GrpcConfiguration(String host, int port) {
    public GrpcConfiguration {
        if (host == null || host.isBlank()) {
            throw new IllegalArgumentException("Host cannot be null or blank.");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Port must be between 1 and 65535.");
        }
    }
}
