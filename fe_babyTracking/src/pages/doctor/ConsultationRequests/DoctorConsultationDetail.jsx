// src/pages/doctor/consultation/DoctorConsultationDetail.jsx
import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  getConsultationReplies,
  addNewConsultationReply,
} from "../../../services/APIServices";
import toast from "react-hot-toast";

/**
 * Hiển thị chi tiết 1 Consultation + Discussion
 * @param {object} requestData - object {id, status, requestTitle, note, ...}
 * @param {function} onBack - hàm quay lại board
 */
export default function DoctorConsultationDetail({ requestData, onBack }) {
  const [consultationDetail, setConsultationDetail] = useState(requestData);

  const [replies, setReplies] = useState([]);
  const [newReply, setNewReply] = useState("");

  useEffect(() => {
    const fetchConsultationReplies = async () => {
      try {
        const result = await getConsultationReplies(consultationDetail.id);
        setReplies(result);
      } catch (error) {
        console.log(error);
      }
    };
    fetchConsultationReplies();
  }, [consultationDetail.id]);

  const handleAddReply = async () => {
    try {
      const result = await addNewConsultationReply(
        consultationDetail.id,
        newReply
      );
      if (result) {
        toast.success("Response success!");
        onBack();
      } else {
        toast.error("Response failed!");
      }
    } catch (error) {
      console.log(error);
    }
  };

  if (!consultationDetail) {
    return (
      <div className="p-4">
        <p className="text-red-500">No consultation data!</p>
        <button onClick={onBack} className="underline text-blue-500">
          Back
        </button>
      </div>
    );
  }

  const { requestTitle, note, requestDate, status, child } = consultationDetail;

  return (
    <div className="p-4 bg-white rounded shadow-sm">
      <button
        onClick={onBack}
        className="mb-4 text-blue-500 underline hover:text-blue-700"
      >
        &larr; Back
      </button>

      <h2 className="text-2xl font-bold mb-2">{requestTitle || "No Title"}</h2>
      <p className="text-gray-600 mb-2">
        Request Date: {requestDate?.slice(0, 10)}
      </p>
      <p className="text-gray-800 mb-2">Status: {status}</p>
      <p className="text-gray-800 mb-4">Note: {note}</p>

      {child && (
        <div className="mb-4 p-2 border rounded bg-gray-50">
          <h4 className="font-semibold">Child Info</h4>
          <p>Name: {child.name}</p>
          <p>Birth: {child.birthDate?.slice(0, 10)}</p>
          <p>Gender: {child.gender}</p>
          <p>Parent: {child.parentName}</p>
        </div>
      )}

      {/* Discussion */}
      <div className="mt-4">
        <h3 className="text-lg font-semibold mb-2">Discussion</h3>
        <div className="space-y-2 mb-4">
          {replies?.map((r) => {
            const authorName = r.user
              ? `${r.user.firstName} ${r.user.lastName}`
              : "Unknown";
            return (
              <div key={r.id} className="p-2 border rounded">
                <strong>{authorName}</strong> ({r.user?.role}): {r.content}
                <p className="text-xs text-gray-400">
                  {r.createdAt?.slice(0, 16)}
                </p>
              </div>
            );
          })}
        </div>
        {status === "PROCESSING" || status === "ASSIGNED" ? (
          <div className="flex gap-2">
            <input
              className="flex-1 border p-2 rounded"
              placeholder="Type your reply..."
              value={newReply}
              onChange={(e) => setNewReply(e.target.value)}
            />
            <button
              onClick={handleAddReply}
              className="bg-blue-500 text-white px-4 py-2 rounded"
            >
              Send
            </button>
          </div>
        ) : (
          <></>
        )}
        {(status === "CLOSED" || status === "CANCELLED") && (
          <p className="text-gray-500 mt-2">
            This request is {status}. Read-only.
          </p>
        )}
      </div>
    </div>
  );
}
