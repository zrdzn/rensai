"use client";

import { useState } from "react";
import { Save, Activity, Key, Link2, FileText, CheckCircle2 } from "lucide-react";

export default function ConfigurationPage() {
    const [endpoint, setEndpoint] = useState("https://api-gateway.internal.network/v1");
    const [apiKey, setApiKey] = useState("");
    const [privateKey, setPrivateKey] = useState("");
    const [isSaving, setIsSaving] = useState(false);
    const [saveSuccess, setSaveSuccess] = useState(false);
    const [isTesting, setIsTesting] = useState(false);

    const handleSave = (e: React.FormEvent) => {
        e.preventDefault();
        setIsSaving(true);
        setSaveSuccess(false);

        setTimeout(() => {
            setIsSaving(false);
            setSaveSuccess(true);
            setTimeout(() => setSaveSuccess(false), 3000);
        }, 1000);
    };

    const handleTestConnection = () => {
        setIsTesting(true);
        setTimeout(() => {
            setIsTesting(false);
            alert("Connection to API Gateway successful!");
        }, 1500);
    };

    return (
        <div className="p-8 max-w-4xl mx-auto">
            <div className="mb-8">
                <h1 className="text-2xl font-bold text-gray-900">System Configuration</h1>
                <p className="text-sm text-gray-500 mt-1">
                    Manage your global settings and API Gateway connectivity.
                </p>
            </div>

            <div className="bg-white border border-gray-200 rounded-xl shadow-sm overflow-hidden">
                <div className="px-6 py-5 border-b border-gray-200 bg-gray-50">
                    <h2 className="text-lg font-semibold text-gray-900 flex items-center gap-2">
                        <Activity className="w-5 h-5 text-blue-600" />
                        API Gateway Connection
                    </h2>
                    <p className="text-sm text-gray-500 mt-1">
                        Configure authentication to allow the application to route requests, fetch logs, and dispatch tasks via the API Gateway.
                    </p>
                </div>

                <form onSubmit={handleSave} className="p-6 space-y-6">
                    <div>
                        <label htmlFor="endpoint" className="block text-sm font-medium text-gray-700 mb-1 flex items-center gap-2">
                            <Link2 className="w-4 h-4 text-gray-400" />
                            Endpoint URL
                        </label>
                        <input
                            type="url"
                            id="endpoint"
                            value={endpoint}
                            onChange={(e) => setEndpoint(e.target.value)}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-shadow text-sm"
                            placeholder=""
                            required
                        />
                    </div>

                    <div>
                        <label htmlFor="apiKey" className="block text-sm font-medium text-gray-700 mb-1 flex items-center gap-2">
                            <Key className="w-4 h-4 text-gray-400" />
                            API Key
                        </label>
                        <input
                            type="password"
                            id="apiKey"
                            value={apiKey}
                            onChange={(e) => setApiKey(e.target.value)}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-shadow text-sm font-mono"
                            placeholder="sk_live_..."
                            required
                        />
                        <p className="text-xs text-gray-500 mt-1">
                            Requires 'logs:read' and 'agents:write' permissions.
                        </p>
                    </div>

                    <div>
                        <label htmlFor="privateKey" className="block text-sm font-medium text-gray-700 mb-1 flex items-center gap-2">
                            <FileText className="w-4 h-4 text-gray-400" />
                            RSA Private Key (Optional)
                        </label>
                        <textarea
                            id="privateKey"
                            value={privateKey}
                            onChange={(e) => setPrivateKey(e.target.value)}
                            rows={6}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-shadow text-sm font-mono resize-y"
                            placeholder="-----BEGIN RSA PRIVATE KEY-----&#10;...&#10;-----END RSA PRIVATE KEY-----"
                        />
                        <p className="text-xs text-gray-500 mt-1">
                            Required only if your API Gateway enforces mutual TLS (mTLS) authentication.
                        </p>
                    </div>

                    <div className="pt-4 border-t border-gray-200 flex items-center justify-between">
                        <button
                            type="button"
                            onClick={handleTestConnection}
                            disabled={isTesting || !apiKey || !endpoint}
                            className="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-lg hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
                        >
                            {isTesting ? "Testing..." : "Test Connection"}
                        </button>

                        <div className="flex items-center gap-4">
                            {saveSuccess && (
                                <span className="text-sm text-green-600 flex items-center gap-1.5 font-medium">
                  <CheckCircle2 className="w-4 h-4" />
                  Saved successfully
                </span>
                            )}
                            <button
                                type="submit"
                                disabled={isSaving}
                                className="flex items-center gap-2 px-6 py-2 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
                            >
                                <Save className="w-4 h-4" />
                                {isSaving ? "Saving..." : "Save Settings"}
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
}