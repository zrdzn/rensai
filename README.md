# Rensai
A simple yet extensible game event orchestrator designed to notify all configured integrations about detailed game events.

## Architecture
### rensai-agents
A collection of direct game event receivers. Each agent captures game events and sends them as gRPC requests to the `rensai-ingestion` module.
### rensai-ingestion
The central gRPC ingestion server. It handles raw request validation and deserialization, then pushes the transformed data directly to the orchestrator via Kafka.
### rensai-orchestrator 
The core business logic module. It processes transformed requests from `rensai-ingestion`, handles asynchronous configuration updates from `rensai-console`, 
and dispatches actions to the appropriate integrations in `rensai-integrations`.
### rensai-integrations
A collection of all supported integrations. Once the orchestrator triggers an event flow, these modules execute the final actions, such as sending emails or posting Discord messages via bots.
### rensai-console
The main dashboard used to configure nodes and manage the entire event flow from the initial game event down to the final integration.
### rensai-api-gateway
A lightweight proxy bridge that facilitates asynchronous communication between `rensai-console` and `rensai-orchestrator`.
### rensai-common
A shared library containing common data models, gRPC proto definitions, and utility code.
