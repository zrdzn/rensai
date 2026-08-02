"use client";

import { useState } from "react";
import Link from "next/link";
import { Workflow, Users, Puzzle, Settings, ChevronDown, Check } from "lucide-react";

const workspaces = [
    { id: "1", name: "workspace-1" },
    { id: "2", name: "workspace-2" },
    { id: "3", name: "workspace-3" },
];

const sidebarItems = [
    { name: "Flow", href: "/flow", icon: Workflow },
    { name: "Agents", href: "/agents", icon: Users },
    { name: "Integrations", href: "/integrations", icon: Puzzle },
    { name: "Configuration", href: "/configuration", icon: Settings },
];

export default function Sidebar() {
    const [isOpen, setIsOpen] = useState(false);
    const [activeWorkspace, setActiveWorkspace] = useState(workspaces[0]);

    return (
        <aside className="w-64 h-screen bg-white border-r border-gray-200 flex flex-col">
            <div className="h-16 flex items-center px-6 border-b border-gray-200">
        <span className="text-xl font-bold text-gray-900 tracking-tight">
          Rensai
        </span>
            </div>

            <div className="px-4 pt-6 pb-2 relative">
                <button
                    onClick={() => setIsOpen(!isOpen)}
                    className="w-full flex items-center justify-between px-3 py-2 bg-gray-50 border border-gray-200 rounded-md hover:bg-gray-100 transition-colors"
                >
          <span className="text-sm font-medium text-gray-700 truncate">
            {activeWorkspace.name}
          </span>
                    <ChevronDown className={`w-4 h-4 text-gray-500 transition-transform ${isOpen ? "rotate-180" : ""}`} />
                </button>

                {isOpen && (
                    <div className="absolute top-full left-4 right-4 mt-1 bg-white border border-gray-200 rounded-md shadow-lg overflow-hidden z-10">
                        {workspaces.map((workspace) => (
                            <button
                                key={workspace.id}
                                onClick={() => {
                                    setActiveWorkspace(workspace);
                                    setIsOpen(false);
                                }}
                                className="w-full flex items-center justify-between px-3 py-2 text-sm text-left text-gray-700 hover:bg-gray-50 transition-colors"
                            >
                                <span className="truncate">{workspace.name}</span>
                                {activeWorkspace.id === workspace.id && (
                                    <Check className="w-4 h-4 text-blue-600" />
                                )}
                            </button>
                        ))}
                    </div>
                )}
            </div>

            <nav className="flex-1 px-4 py-2 space-y-1 overflow-y-auto">
                {sidebarItems.map((item) => {
                    const Icon = item.icon;
                    return (
                        <Link
                            key={item.name}
                            href={item.href}
                            className="flex items-center gap-3 px-3 py-2 text-sm text-gray-600 rounded-md hover:bg-gray-100 hover:text-gray-900 transition-colors"
                        >
                            <Icon className="w-4 h-4" />
                            <span className="font-medium">{item.name}</span>
                        </Link>
                    );
                })}
            </nav>
        </aside>
    );
}