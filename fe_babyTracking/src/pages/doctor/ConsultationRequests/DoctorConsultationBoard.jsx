import React from "react";
import {
  ClockIcon,
  LightningBoltIcon,
  XCircleIcon,
} from "@heroicons/react/outline";
import KanbanCard from "./KanbanCard";

export default function DoctorConsultationBoard({ requests, onSelectRequest }) {
  const pendingList = requests?.filter((r) => r.status === "PENDING");
  const processingList = requests?.filter((r) => r.status === "ASSIGNED");
  const closedList = requests?.filter(
    (r) => r.status === "CLOSED" || r.status === "CANCELED"
  );

  return (
    <div className="p-4">
      <h2 className="text-2xl font-bold mb-6">Doctor Consultation Board</h2>
      <div className="grid grid-cols-3 gap-4">
        {/* Pending */}
        <div className="bg-gray-100 p-4 rounded shadow-sm">
          <div className="flex items-center gap-2 mb-2">
            <ClockIcon className="h-5 w-5 text-yellow-500" />
            <h3 className="text-lg font-semibold">Pending</h3>
          </div>
          <div className="space-y-2">
            {pendingList?.map((req) => (
              <KanbanCard
                key={req.id}
                consultation={req}
                onSelect={() => onSelectRequest(req)}
              />
            ))}
          </div>
        </div>

        {/* Processing */}
        <div className="bg-gray-100 p-4 rounded shadow-sm">
          <div className="flex items-center gap-2 mb-2">
            <LightningBoltIcon className="h-5 w-5 text-blue-500" />
            <h3 className="text-lg font-semibold">Processing</h3>
          </div>
          <div className="space-y-2">
            {processingList?.map((req) => (
              <KanbanCard
                key={req.id}
                consultation={req}
                onSelect={() => onSelectRequest(req)}
              />
            ))}
          </div>
        </div>

        {/* Closed / Canceled */}
        <div className="bg-gray-100 p-4 rounded shadow-sm">
          <div className="flex items-center gap-2 mb-2">
            <XCircleIcon className="h-5 w-5 text-red-500" />
            <h3 className="text-lg font-semibold">Closed / Canceled</h3>
          </div>
          <div className="space-y-2">
            {closedList?.map((req) => (
              <KanbanCard
                key={req.id}
                consultation={req}
                onSelect={() => onSelectRequest(req)}
              />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
