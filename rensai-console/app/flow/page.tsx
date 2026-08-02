"use client";

import React, { useState, useCallback, memo } from 'react';
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
import { Server, Zap, HelpCircle, Puzzle, Plus, Trash2 } from 'lucide-react';

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
                className="w-full text-sm border border-gray-300 bg-gray-50 p-2 rounded-md outline-none focus:border-blue-500 nodrag"
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
                className="w-full text-sm border border-gray-300 bg-gray-50 p-2 rounded-md outline-none focus:border-yellow-500 nodrag"
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
    const conditions = data.conditions || [];

    const addCondition = () => {
        updateNodeData(id, {
            conditions: [...conditions, { property: 'eventName', operator: 'Equals', value: '' }]
        });
    };

    const updateCondition = (index: number, field: string, value: string) => {
        const newConditions = [...conditions];
        newConditions[index] = { ...newConditions[index], [field]: value };
        updateNodeData(id, { conditions: newConditions });
    };

    const removeCondition = (index: number) => {
        const newConditions = conditions.filter((_: any, i: number) => i !== index);
        updateNodeData(id, { conditions: newConditions });
    };

    return (
        <div className="bg-white border-2 border-purple-200 rounded-lg p-4 w-72 shadow-sm">
            <Handle type="target" position={Position.Left} isConnectable={isConnectable} className="w-3 h-3 bg-purple-500" />
            <div className="flex items-center justify-between mb-3">
                <div className="flex items-center gap-2 font-bold text-sm text-purple-700">
                    <HelpCircle className="w-4 h-4" />
                    Condition
                </div>
            </div>

            <div className="space-y-2 mb-3">
                {conditions.map((cond: any, index: number) => (
                    <div key={index} className="flex gap-2 items-start border border-purple-100 bg-purple-50 p-2 rounded-md">
                        <div className="flex-1 space-y-2">
                            <select
                                className="w-full text-xs border border-gray-300 bg-white p-1.5 rounded outline-none focus:border-purple-500 nodrag"
                                value={cond.property}
                                onChange={(e) => updateCondition(index, 'property', e.target.value)}
                            >
                                <option value="eventName">getEventName()</option>
                                <option value="gameSource">getGameSource()</option>
                                <option value="timestamp">getTimestamp()</option>
                                <option value="properties">getProperties() (Custom Key)</option>
                            </select>

                            {cond.property === 'properties' && (
                                <input
                                    type="text"
                                    placeholder="Property Key (e.g. block_type)"
                                    className="w-full text-xs border border-gray-300 bg-white p-1.5 rounded outline-none focus:border-purple-500 nodrag"
                                    value={cond.customKey || ""}
                                    onChange={(e) => updateCondition(index, 'customKey', e.target.value)}
                                />
                            )}

                            <select
                                className="w-full text-xs border border-gray-300 bg-white p-1.5 rounded outline-none focus:border-purple-500 nodrag"
                                value={cond.operator}
                                onChange={(e) => updateCondition(index, 'operator', e.target.value)}
                            >
                                <option value="Equals">Equals</option>
                                <option value="LessThan">Less Than</option>
                                <option value="MoreThan">More Than</option>
                                <option value="IsTrue">Is True</option>
                                <option value="IsFalse">Is False</option>
                            </select>

                            {!['IsTrue', 'IsFalse'].includes(cond.operator) && (
                                <input
                                    type="text"
                                    placeholder="Value"
                                    className="w-full text-xs border border-gray-300 bg-white p-1.5 rounded outline-none focus:border-purple-500 nodrag"
                                    value={cond.value || ""}
                                    onChange={(e) => updateCondition(index, 'value', e.target.value)}
                                />
                            )}
                        </div>
                        <button
                            onClick={() => removeCondition(index)}
                            className="text-gray-400 hover:text-red-600 p-1 nodrag"
                        >
                            <Trash2 className="w-4 h-4" />
                        </button>
                    </div>
                ))}
                {conditions.length === 0 && (
                    <div className="text-xs text-gray-500 text-center py-2">No conditions set.</div>
                )}
            </div>

            <button
                onClick={addCondition}
                className="w-full flex items-center justify-center gap-1 bg-purple-100 text-purple-700 py-1.5 rounded-md text-xs font-semibold hover:bg-purple-200 transition-colors nodrag"
            >
                <Plus className="w-3 h-3" /> Add Condition
            </button>

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
                    className="w-full text-sm border border-gray-300 bg-gray-50 p-2 rounded-md outline-none focus:border-indigo-500 nodrag"
                    value={data.selectedIntegration || ""}
                    onChange={(e) => updateNodeData(id, { selectedIntegration: e.target.value })}
                >
                    <option value="" disabled>Select Integration...</option>
                    <option value="discord">Discord</option>
                    <option value="teams">Teams</option>
                    <option value="mail">Mail</option>
                </select>
                <select
                    className="w-full text-sm border border-gray-300 bg-gray-50 p-2 rounded-md outline-none focus:border-indigo-500 nodrag"
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