"use client";

import { useState, useCallback, memo } from 'react';
import {
    ReactFlow,
    ReactFlowProvider,
    applyNodeChanges,
    applyEdgeChanges,
    addEdge,
    useReactFlow,
    Background,
    Controls,
    Connection,
    Edge,
    Node,
    NodeChange,
    EdgeChange,
    Position,
    Handle
} from '@xyflow/react';
import '@xyflow/react/dist/style.css';
import { Server, Zap, HelpCircle, Puzzle } from 'lucide-react';

let id = 0;
const getId = () => `node_${id++}`;

const AgentNode = memo(({ id, data, isConnectable }: any) => {
    const { updateNodeData } = useReactFlow();
    return (
        <div className="bg-white border-2 border-blue-200 rounded-lg p-4 w-56 shadow-sm">
            <div className="flex items-center gap-2 font-bold text-sm text-blue-700 mb-3">
                <Server className="w-4 h-4" />
                Agent
            </div>
            <select
                className="w-full text-sm border border-gray-300 bg-gray-50 p-2 rounded-md outline-none focus:border-blue-500"
                value={data.selectedAgent || ""}
                onChange={(e) => updateNodeData(id, { selectedAgent: e.target.value })}
            >
                <option value="" disabled>Select Agent...</option>
                <option value="paper-1">Paper agent-1</option>
                <option value="factorio-1">Factorio agent-1</option>
            </select>
            <Handle type="source" position={Position.Right} isConnectable={isConnectable} className="w-3 h-3 bg-blue-500" />
        </div>
    );
});
AgentNode.displayName = "AgentNode";

const EventNode = memo(({ id, data, isConnectable }: any) => {
    const { updateNodeData } = useReactFlow();
    return (
        <div className="bg-white border-2 border-yellow-200 rounded-lg p-4 w-56 shadow-sm">
            <Handle type="target" position={Position.Left} isConnectable={isConnectable} className="w-3 h-3 bg-yellow-500" />
            <div className="flex items-center gap-2 font-bold text-sm text-yellow-700 mb-3">
                <Zap className="w-4 h-4" />
                Event
            </div>
            <select
                className="w-full text-sm border border-gray-300 bg-gray-50 p-2 rounded-md outline-none focus:border-yellow-500"
                value={data.selectedEvent || ""}
                onChange={(e) => updateNodeData(id, { selectedEvent: e.target.value })}
            >
                <option value="" disabled>Select Event...</option>
                <option value="block_break">BlockBreakEvent</option>
                <option value="player_join">PlayerJoinEvent</option>
                <option value="server_start">ServerStartEvent</option>
            </select>
            <Handle type="source" position={Position.Right} isConnectable={isConnectable} className="w-3 h-3 bg-yellow-500" />
        </div>
    );
});
EventNode.displayName = "EventNode";

const ConditionNode = memo(({ id, data, isConnectable }: any) => {
    const { updateNodeData } = useReactFlow();
    return (
        <div className="bg-white border-2 border-purple-200 rounded-lg p-4 w-56 shadow-sm">
            <Handle type="target" position={Position.Left} isConnectable={isConnectable} className="w-3 h-3 bg-purple-500" />
            <div className="flex items-center gap-2 font-bold text-sm text-purple-700 mb-3">
                <HelpCircle className="w-4 h-4" />
                Condition
            </div>
            <input
                type="text"
                placeholder="e.g. event.hasItem()"
                className="w-full text-sm border border-gray-300 bg-gray-50 p-2 rounded-md outline-none focus:border-purple-500"
                value={data.conditionValue || ""}
                onChange={(e) => updateNodeData(id, { conditionValue: e.target.value })}
            />
            <Handle type="source" position={Position.Right} isConnectable={isConnectable} className="w-3 h-3 bg-purple-500" />
        </div>
    );
});
ConditionNode.displayName = "ConditionNode";

const IntegrationNode = memo(({ id, data, isConnectable }: any) => {
    const { updateNodeData } = useReactFlow();
    return (
        <div className="bg-white border-2 border-indigo-200 rounded-lg p-4 w-56 shadow-sm">
            <Handle type="target" position={Position.Left} isConnectable={isConnectable} className="w-3 h-3 bg-indigo-500" />
            <div className="flex items-center gap-2 font-bold text-sm text-indigo-700 mb-3">
                <Puzzle className="w-4 h-4" />
                Integration
            </div>
            <div className="space-y-2">
                <select
                    className="w-full text-sm border border-gray-300 bg-gray-50 p-2 rounded-md outline-none focus:border-indigo-500"
                    value={data.selectedIntegration || ""}
                    onChange={(e) => updateNodeData(id, { selectedIntegration: e.target.value })}
                >
                    <option value="" disabled>Select Integration...</option>
                    <option value="discord">Discord</option>
                    <option value="teams">Teams</option>
                    <option value="mail">Mail</option>
                </select>
                <select
                    className="w-full text-sm border border-gray-300 bg-gray-50 p-2 rounded-md outline-none focus:border-indigo-500"
                    value={data.selectedAction || ""}
                    onChange={(e) => updateNodeData(id, { selectedAction: e.target.value })}
                >
                    <option value="" disabled>Select Action...</option>
                    <option value="send_message">Send Message</option>
                    <option value="alert_admin">Alert Admin</option>
                    <option value="execute_webhook">Execute Webhook</option>
                </select>
            </div>
            <Handle type="source" position={Position.Right} isConnectable={isConnectable} className="w-3 h-3 bg-indigo-500" />
        </div>
    );
});
IntegrationNode.displayName = "IntegrationNode";

const nodeTypes = {
    Agent: AgentNode,
    Event: EventNode,
    Condition: ConditionNode,
    Integration: IntegrationNode,
};

const toolbarItems = [
    { type: 'Agent', label: 'Agent', icon: Server, color: 'bg-blue-50 text-blue-700 border-blue-200' },
    { type: 'Event', label: 'Event', icon: Zap, color: 'bg-yellow-50 text-yellow-700 border-yellow-200' },
    { type: 'Condition', label: 'Condition', icon: HelpCircle, color: 'bg-purple-50 text-purple-700 border-purple-200' },
    { type: 'Integration', label: 'Integration', icon: Puzzle, color: 'bg-indigo-50 text-indigo-700 border-indigo-200' },
];

function FlowEditor() {
    const { screenToFlowPosition } = useReactFlow();
    const [nodes, setNodes] = useState<Node[]>([]);
    const [edges, setEdges] = useState<Edge[]>([]);

    const onNodesChange = useCallback(
        (changes: NodeChange[]) => setNodes((nds) => applyNodeChanges(changes, nds)),
        []
    );

    const onEdgesChange = useCallback(
        (changes: EdgeChange[]) => setEdges((eds) => applyEdgeChanges(changes, eds)),
        []
    );

    const onConnect = useCallback(
        (params: Connection) => setEdges((eds) => addEdge(params, eds)),
        []
    );

    const onDragStart = (event: React.DragEvent<HTMLDivElement>, nodeType: string) => {
        event.dataTransfer.setData('application/reactflow/type', nodeType);
        event.dataTransfer.effectAllowed = 'move';
    };

    const onDragOver = useCallback((event: React.DragEvent<HTMLDivElement>) => {
        event.preventDefault();
        event.dataTransfer.dropEffect = 'move';
    }, []);

    const onDrop = useCallback(
        (event: React.DragEvent<HTMLDivElement>) => {
            event.preventDefault();

            const type = event.dataTransfer.getData('application/reactflow/type');

            if (!type) {
                return;
            }

            const position = screenToFlowPosition({
                x: event.clientX,
                y: event.clientY,
            });

            const newNode: Node = {
                id: getId(),
                type,
                position,
                data: {},
            };

            setNodes((nds) => nds.concat(newNode));
        },
        [screenToFlowPosition]
    );

    return (
        <div className="flex flex-col h-full w-full">
            <div className="flex flex-wrap gap-3 p-4 bg-white border-b border-gray-200 shadow-sm z-10">
                {toolbarItems.map((item) => {
                    const Icon = item.icon;
                    return (
                        <div
                            key={item.type}
                            className={`flex items-center gap-2 px-3 py-1.5 rounded-md border cursor-grab active:cursor-grabbing hover:shadow-md transition-all ${item.color}`}
                            onDragStart={(event) => onDragStart(event, item.type)}
                            draggable
                        >
                            <Icon className="w-4 h-4" />
                            <span className="text-sm font-medium">{item.label}</span>
                        </div>
                    );
                })}
            </div>

            <div className="flex-1 w-full bg-gray-50 relative">
                <ReactFlow
                    nodes={nodes}
                    edges={edges}
                    onNodesChange={onNodesChange}
                    onEdgesChange={onEdgesChange}
                    onConnect={onConnect}
                    onDrop={onDrop}
                    onDragOver={onDragOver}
                    nodeTypes={nodeTypes}
                    fitView
                >
                    <Background />
                    <Controls />
                </ReactFlow>
            </div>
        </div>
    );
}

export default function FlowPage() {
    return (
        <div className="h-full w-full">
            <ReactFlowProvider>
                <FlowEditor />
            </ReactFlowProvider>
        </div>
    );
}