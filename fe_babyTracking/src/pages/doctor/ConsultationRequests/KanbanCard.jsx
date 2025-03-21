// src/pages/doctor/consultation/KanbanCard.jsx
import React from "react";

export default function KanbanCard({ consultation, onSelect }) {
    const { requestTitle, requestDate, status } = consultation;

    return (
        <div
            className="bg-white p-3 rounded shadow-sm cursor-pointer hover:bg-gray-50"
            onClick={onSelect}
        >
            <h4 className="text-gray-800 font-semibold mb-1">
                {requestTitle || "No Title"}
            </h4>
            <p className="text-sm text-gray-600 mb-1">
                Date: {requestDate?.slice(0, 10)}
            </p>
            <p className="text-sm text-gray-500">Status: {status}</p>
        </div>
    );
}
