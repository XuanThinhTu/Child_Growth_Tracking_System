// src/pages/doctor/consultation/index.jsx
import React, { useEffect, useState } from "react";
import axios from "axios";

import DoctorConsultationBoard from "./DoctorConsultationBoard";
import DoctorConsultationDetail from "./DoctorConsultationDetail";
import { getAllConsultations } from "../../../services/APIServices";

// Giả sử baseUrl từ env
const baseUrl = import.meta.env.VITE_API_BASE_URL;

/**
 * Đây là component "chính" cho Consultation:
 * - Lấy danh sách requests => hiển thị Board
 * - Khi bấm 1 request => hiển thị Detail
 */
export default function DoctorConsultation() {
  const [requests, setRequests] = useState([]);
  const doctorId = sessionStorage.getItem("userId");
  const [selectedRequest, setSelectedRequest] = useState(null); // object
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchAllConsultations = async () => {
      try {
        setLoading(true);
        const result = await getAllConsultations();
        const filteredData = result.filter(
          (item) => item?.doctorId === parseInt(doctorId)
        );
        setRequests(filteredData);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    };
    fetchAllConsultations();
  }, []);

  const handleSelectRequest = (req) => {
    setSelectedRequest(req);
  };

  const handleBackToBoard = () => {
    setSelectedRequest(null);
  };

  if (loading) {
    return <p className="p-4">Loading consultations...</p>;
  }

  if (!selectedRequest) {
    return (
      <DoctorConsultationBoard
        requests={requests}
        onSelectRequest={handleSelectRequest}
      />
    );
  }

  return (
    <DoctorConsultationDetail
      requestData={selectedRequest}
      onBack={handleBackToBoard}
    />
  );
}
