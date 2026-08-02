"use client";

import { useState } from "react";
import { LayoutGrid, List, Plus, Trash2, Mail, MessageSquare, Hash, Webhook } from "lucide-react";

type IntegrationType = "Mail" | "Teams" | "Discord" | "Slack";

type Integration = {
    id: string;
    name: string;
    type: IntegrationType;
    description: string;
};

const initialIntegrations: Integration[] = [
    {
        id: "1",
        name: "Admin Alerts",
        type: "Mail",
        description: "Sends critical failure alerts to the admin team",
    },
    {
        id: "2",
        name: "DevOps Channel",
        type: "Teams",
        description: "Deployment status notifications",
    },
    {
        id: "3",
        name: "Community Webhook",
        type: "Discord",
        description: "Posts game server restarts to the community server",
    },
];

const typeStyles: Record<IntegrationType, { bg: string; text: string; icon: React.ElementType }> = {
    Mail: { bg: "bg-blue-100", text: "text-blue-700", icon: Mail },
    Teams: { bg: "bg-purple-100", text: "text-purple-700", icon: MessageSquare },
    Discord: { bg: "bg-indigo-100", text: "text-indigo-700", icon: Webhook },
    Slack: { bg: "bg-green-100", text: "text-green-700", icon: Hash },
};

export default function IntegrationsPage() {
    const [integrations, setIntegrations] = useState<Integration[]>(initialIntegrations);
    const [viewMode, setViewMode] = useState<"card" | "list">("card");

    const handleCreateIntegration = (type: IntegrationType) => {
        const newId = Math.random().toString(36).substring(7);
        const newIntegration: Integration = {
            id: newId,
            name: `New ${type} Hook-${newId.substring(0, 4)}`,
            type,
            description: `Automatically generated ${type} integration`,
        };
        setIntegrations([...integrations, newIntegration]);
    };

    const handleDeleteIntegration = (id: string) => {
        setIntegrations(integrations.filter((integration) => integration.id !== id));
    };

    return (
        <div className="p-8 max-w-7xl mx-auto">
            <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-8">
                <h1 className="text-2xl font-bold text-gray-900">Integrations Management</h1>

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

            <div className="flex flex-wrap gap-4 mb-8">
                {(Object.keys(typeStyles) as IntegrationType[]).map((type) => (
                    <button
                        key={type}
                        onClick={() => handleCreateIntegration(type)}
                        className="flex items-center gap-2 bg-gray-900 text-white px-4 py-2 rounded-lg hover:bg-gray-800 transition-colors font-medium text-sm"
                    >
                        <Plus className="w-4 h-4" />
                        Add {type}
                    </button>
                ))}
            </div>

            {viewMode === "card" ? (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {integrations.map((integration) => {
                        const Icon = typeStyles[integration.type].icon;
                        const style = typeStyles[integration.type];
                        return (
                            <div key={integration.id} className="bg-white border border-gray-200 rounded-xl p-6 shadow-sm hover:shadow-md transition-shadow">
                                <div className="flex justify-between items-start mb-4">
                                    <div className="flex items-center gap-3">
                                        <div className={`p-3 rounded-lg ${style.bg} ${style.text}`}>
                                            <Icon className="w-6 h-6" />
                                        </div>
                                        <div>
                                            <h3 className="font-semibold text-gray-900">{integration.name}</h3>
                                            <span className="text-xs font-medium text-gray-500 uppercase tracking-wider">{integration.type}</span>
                                        </div>
                                    </div>
                                    <button
                                        onClick={() => handleDeleteIntegration(integration.id)}
                                        className="p-2 text-gray-400 hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors"
                                    >
                                        <Trash2 className="w-5 h-5" />
                                    </button>
                                </div>
                                <p className="text-sm text-gray-600">{integration.description}</p>
                            </div>
                        );
                    })}
                    {integrations.length === 0 && (
                        <div className="col-span-full text-center py-12 text-gray-500 bg-gray-50 rounded-xl border border-dashed border-gray-300">
                            No integrations configured. Add one above.
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
                        {integrations.map((integration) => {
                            const Icon = typeStyles[integration.type].icon;
                            const style = typeStyles[integration.type];
                            return (
                                <tr key={integration.id} className="hover:bg-gray-50 transition-colors">
                                    <td className="px-6 py-4 font-medium text-gray-900 flex items-center gap-3">
                                        <Icon className={`w-4 h-4 ${style.text}`} />
                                        {integration.name}
                                    </td>
                                    <td className="px-6 py-4">
                      <span className={`px-2.5 py-1 rounded-full text-xs font-medium ${style.bg} ${style.text}`}>
                        {integration.type}
                      </span>
                                    </td>
                                    <td className="px-6 py-4">{integration.description}</td>
                                    <td className="px-6 py-4 text-right">
                                        <button
                                            onClick={() => handleDeleteIntegration(integration.id)}
                                            className="text-gray-400 hover:text-red-600 transition-colors"
                                        >
                                            <Trash2 className="w-5 h-5 inline" />
                                        </button>
                                    </td>
                                </tr>
                            );
                        })}
                        {integrations.length === 0 && (
                            <tr>
                                <td colSpan={4} className="px-6 py-12 text-center text-gray-500">
                                    No integrations configured. Add one above.
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