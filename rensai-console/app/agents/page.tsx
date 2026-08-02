"use client";

import { useState } from "react";
import { LayoutGrid, List, Plus, Trash2, Server } from "lucide-react";

type Agent = {
    id: string;
    name: string;
    type: "Paper" | "Factorio";
    description: string;
};

const initialAgents: Agent[] = [
    {
        id: "1",
        name: "Paper agent-1",
        type: "Paper",
        description: "Main survival server - Node A",
    },
    {
        id: "2",
        name: "Factorio agent-1",
        type: "Factorio",
        description: "Space - Node B",
    },
];

export default function AgentsPage() {
    const [agents, setAgents] = useState<Agent[]>(initialAgents);
    const [viewMode, setViewMode] = useState<"card" | "list">("card");

    const handleCreateAgent = (type: "Paper" | "Factorio") => {
        const newId = Math.random().toString(36).substring(7);
        const newAgent: Agent = {
            id: newId,
            name: `${type} agent-${newId.substring(0, 4)}`,
            type,
            description: `New ${type} server instance`,
        };
        setAgents([...agents, newAgent]);
    };

    const handleDeleteAgent = (id: string) => {
        setAgents(agents.filter((agent) => agent.id !== id));
    };

    return (
        <div className="p-8 max-w-7xl mx-auto">
            <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-8">
                <h1 className="text-2xl font-bold text-gray-900">Agents Management</h1>

                <div className="flex items-center gap-2 bg-gray-100 p-1 rounded-lg">
                    <button
                        onClick={() => setViewMode("card")}
                        className={`p-2 rounded-md transition-colors ${
                            viewMode === "card" ? "bg-white shadow-sm text-blue-600" : "text-gray-500 hover:text-gray-900"
                        }`}
                    >
                        <LayoutGrid className="w-5 h-5" />
                    </button>
                    <button
                        onClick={() => setViewMode("list")}
                        className={`p-2 rounded-md transition-colors ${
                            viewMode === "list" ? "bg-white shadow-sm text-blue-600" : "text-gray-500 hover:text-gray-900"
                        }`}
                    >
                        <List className="w-5 h-5" />
                    </button>
                </div>
            </div>

            <div className="flex gap-4 mb-8">
                <button
                    onClick={() => handleCreateAgent("Paper")}
                    className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors font-medium text-sm"
                >
                    <Plus className="w-4 h-4" />
                    Create Paper Agent
                </button>
                <button
                    onClick={() => handleCreateAgent("Factorio")}
                    className="flex items-center gap-2 bg-orange-600 text-white px-4 py-2 rounded-lg hover:bg-orange-700 transition-colors font-medium text-sm"
                >
                    <Plus className="w-4 h-4" />
                    Create Factorio Agent
                </button>
            </div>

            {viewMode === "card" ? (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {agents.map((agent) => (
                        <div key={agent.id} className="bg-white border border-gray-200 rounded-xl p-6 shadow-sm hover:shadow-md transition-shadow">
                            <div className="flex justify-between items-start mb-4">
                                <div className="flex items-center gap-3">
                                    <div className={`p-3 rounded-lg ${agent.type === "Paper" ? "bg-blue-100 text-blue-600" : "bg-orange-100 text-orange-600"}`}>
                                        <Server className="w-6 h-6" />
                                    </div>
                                    <div>
                                        <h3 className="font-semibold text-gray-900">{agent.name}</h3>
                                        <span className="text-xs font-medium text-gray-500 uppercase tracking-wider">{agent.type}</span>
                                    </div>
                                </div>
                                <button
                                    onClick={() => handleDeleteAgent(agent.id)}
                                    className="p-2 text-gray-400 hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors"
                                >
                                    <Trash2 className="w-5 h-5" />
                                </button>
                            </div>
                            <p className="text-sm text-gray-600">{agent.description}</p>
                        </div>
                    ))}
                    {agents.length === 0 && (
                        <div className="col-span-full text-center py-12 text-gray-500 bg-gray-50 rounded-xl border border-dashed border-gray-300">
                            No agents running. Create one above.
                        </div>
                    )}
                </div>
            ) : (
                <div className="bg-white border border-gray-200 rounded-xl shadow-sm overflow-hidden">
                    <table className="w-full text-left text-sm text-gray-600">
                        <thead className="bg-gray-50 text-gray-900 border-b border-gray-200">
                        <tr>
                            <th className="px-6 py-4 font-semibold">Name</th>
                            <th className="px-6 py-4 font-semibold">Type</th>
                            <th className="px-6 py-4 font-semibold">Description</th>
                            <th className="px-6 py-4 font-semibold text-right">Actions</th>
                        </tr>
                        </thead>
                        <tbody className="divide-y divide-gray-200">
                        {agents.map((agent) => (
                            <tr key={agent.id} className="hover:bg-gray-50 transition-colors">
                                <td className="px-6 py-4 font-medium text-gray-900 flex items-center gap-3">
                                    <Server className={`w-4 h-4 ${agent.type === "Paper" ? "text-blue-600" : "text-orange-600"}`} />
                                    {agent.name}
                                </td>
                                <td className="px-6 py-4">
                    <span className={`px-2.5 py-1 rounded-full text-xs font-medium ${agent.type === "Paper" ? "bg-blue-100 text-blue-700" : "bg-orange-100 text-orange-700"}`}>
                      {agent.type}
                    </span>
                                </td>
                                <td className="px-6 py-4">{agent.description}</td>
                                <td className="px-6 py-4 text-right">
                                    <button
                                        onClick={() => handleDeleteAgent(agent.id)}
                                        className="text-gray-400 hover:text-red-600 transition-colors"
                                    >
                                        <Trash2 className="w-5 h-5 inline" />
                                    </button>
                                </td>
                            </tr>
                        ))}
                        {agents.length === 0 && (
                            <tr>
                                <td colSpan={4} className="px-6 py-12 text-center text-gray-500">
                                    No agents running. Create one above.
                                </td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}