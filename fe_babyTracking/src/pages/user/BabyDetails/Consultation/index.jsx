// src/pages/doctor/consultation/ConsultationRequest.jsx
import React, { useEffect, useState } from "react";
import {
  ClockIcon,
  LightningBoltIcon,
  XCircleIcon,
  PlusCircleIcon,
} from "@heroicons/react/outline";
import { useNavigate, useParams } from "react-router-dom";

import KanbanCard from "./KanbanCard";
import ConsultationForm from "./ConsultationForm";
import { getUserConsultation } from "../../../../services/APIServices";

export default function ConsultationRequest() {
  const { babyId } = useParams();
  const navigate = useNavigate();
  const [requests, setRequests] = useState([]);
  const [showForm, setShowForm] = useState(false);

  useEffect(() => {
    const fetchUserConsultation = async () => {
      try {
        const result = await getUserConsultation();
        const filteredData = result.filter(
          (item) => item?.child.id === parseInt(babyId)
        );
        setRequests(filteredData);
      } catch (error) {
        console.log(error);
      }
    };
    fetchUserConsultation();
  }, []);

  const pendingRequests = requests?.filter((r) => r.status === "PENDING");
  const processingRequests = requests?.filter((r) => r.status === "ASSIGNED");
  const closedOrCanceled = requests?.filter(
    (r) => r.status === "CLOSED" || r.status === "CANCELED"
  );

  const handleCreate = () => {
    setShowForm(true);
  };

  const handleSaveRequest = (newReq) => {
    setRequests([...requests, newReq]);
    setShowForm(false);
  };

  const handleCancelForm = () => {
    setShowForm(false);
  };

  const handleCardClick = (id) => {
    navigate(`/consultation-detail/${id}`);
  };

  return (
    <div className="p-4">
      {/* Header */}
      <div className="flex items-center justify-between mb-6">
        <h2 className="text-2xl font-bold">Consultation Requests</h2>
        {!showForm && (
          <button
            onClick={handleCreate}
            className="flex items-center gap-1 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          >
            <PlusCircleIcon className="h-5 w-5" />
            Create
          </button>
        )}
      </div>

      {/* Kanban hoặc Form */}
      {!showForm ? (
        <div className="grid grid-cols-3 gap-4">
          {/* Pending */}
          <div className="bg-gray-100 p-4 rounded shadow-sm">
            <div className="flex items-center gap-2 mb-2">
              <ClockIcon className="h-5 w-5 text-yellow-500" />
              <h3 className="text-lg font-semibold">Pending</h3>
            </div>
            <div className="space-y-2">
              {pendingRequests?.map((req) => (
                <div key={req.id} onClick={() => handleCardClick(req.id)}>
                  <KanbanCard req={req} />
                </div>
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
              {processingRequests?.map((req) => (
                <div key={req.id} onClick={() => handleCardClick(req.id)}>
                  <KanbanCard req={req} />
                </div>
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
              {closedOrCanceled?.map((req) => (
                <div key={req.id} onClick={() => handleCardClick(req.id)}>
                  <KanbanCard req={req} />
                </div>
              ))}
            </div>
          </div>
        </div>
      ) : (
        <ConsultationForm
          onSave={handleSaveRequest}
          onCancel={handleCancelForm}
          babyId={babyId}
        />
      )}
    </div>
  );
}
