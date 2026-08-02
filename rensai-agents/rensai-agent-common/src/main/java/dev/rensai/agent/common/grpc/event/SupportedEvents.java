package dev.rensai.agent.common.grpc.event;

import java.util.List;

public record SupportedEvents(String agentId, String agentType, List<String> eventNames) {}
