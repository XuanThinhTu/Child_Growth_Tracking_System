// src/pages/doctor/consultation/KanbanCard.jsx
import React from "react";
import {
  UserIcon,
  QuestionMarkCircleIcon,
  CalendarIcon,
} from "@heroicons/react/outline";

export default function KanbanCard({ req }) {
  return (
    <div className="bg-white p-3 rounded shadow-sm cursor-pointer hover:bg-gray-50">
      <div className="flex items-center gap-2 mb-1 text-gray-700 font-semibold">
        <UserIcon className="h-4 w-4" />
        <span>
          {req.status !== "PENDING"
            ? "dr. " + req.doctorName
            : "No Doctor assigned yet"}
        </span>
      </div>
      <div className="flex items-center gap-2 mb-1 text-gray-700">
        <QuestionMarkCircleIcon className="h-4 w-4" />
        <span>{req.requestTitle}</span>
      </div>
      <div className="flex items-center gap-2 text-sm text-gray-500">
        <CalendarIcon className="h-4 w-4" />
        <span>{req.requestDate?.slice(0, 10)}</span>
      </div>
    </div>
  );
}
