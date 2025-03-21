import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  addNewConsultationReply,
  getConsultationReplies,
  getUserConsultation,
} from "../../../../services/APIServices";
import toast from "react-hot-toast";

export default function ConsultationDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [consultation, setConsultation] = useState(null);
  const [replies, setReplies] = useState([]);
  const [newReply, setNewReply] = useState("");

  useEffect(() => {
    const fetchAllConsultation = async () => {
      try {
        const result = await getUserConsultation();
        const selectedReq = result.find((item) => item.id === parseInt(id));
        setConsultation(selectedReq);
      } catch (error) {
        console.log(error);
      }
    };
    fetchAllConsultation();
  }, [id]);

  useEffect(() => {
    const fetachAllReplies = async () => {
      try {
        const result = await getConsultationReplies(parseInt(id));
        setReplies(result);
      } catch (error) {
        console.log(error);
      }
    };
    fetachAllReplies();
  }, [parseInt(id)]);

  if (!consultation) {
    return (
      <div className="p-6 max-w-2xl mx-auto">
        <p className="text-red-500 font-semibold">Không tìm thấy request!</p>
      </div>
    );
  }

  const { requestTitle, note, requestDate, status, child } = consultation;

  const handleAddReply = async () => {
    try {
      const result = await addNewConsultationReply(parseInt(id), newReply);
      if (result) {
        toast.success("Response success! Please for doctor to reply");
        navigate(`/consultation-request/${consultation.child.id}`);
      } else {
        toast.error("Response failed! Please try again!");
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="p-6 max-w-4xl mx-auto">
      <div className="mb-6">
        <h1 className="text-3xl font-bold text-gray-800">
          Consultation Details
        </h1>
        <p className="text-sm text-gray-500">
          Below is the information regarding this consultation request.
        </p>
        <hr className="mt-4" />
      </div>

      {/* Content layout */}
      <div className="bg-white shadow rounded-lg p-6">
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          {/* Left side: request info */}
          <div>
            <h2 className="text-xl font-semibold text-gray-700 mb-2">
              {requestTitle || "No Title"}
            </h2>
            <p className="text-gray-600 mb-1">
              <span className="font-medium">Request Date:</span>{" "}
              {requestDate?.slice(0, 10)}
            </p>
            <p className="text-gray-600 mb-1">
              <span className="font-medium">Status:</span> {status}
            </p>
            <p className="text-gray-600 mb-4">
              <span className="font-medium">Note:</span> {note}
            </p>
          </div>

          {/* Right side: child info */}
          <div className="bg-gray-50 border border-gray-200 rounded p-4">
            <h4 className="text-lg font-semibold mb-3 text-gray-700">
              Child Information
            </h4>
            {child ? (
              <ul className="space-y-1 text-gray-600">
                <li>
                  <span className="font-medium">Name:</span> {child.name}
                </li>
                <li>
                  <span className="font-medium">Birth:</span>{" "}
                  {child.birthDate?.slice(0, 10)}
                </li>
                <li>
                  <span className="font-medium">Gender:</span> {child.gender}
                </li>
                <li>
                  <span className="font-medium">Parent:</span>{" "}
                  {child.parentName}
                </li>
              </ul>
            ) : (
              <p className="text-gray-500">No child data.</p>
            )}
          </div>
        </div>

        {/* Forum-like UI */}
        {status === "ASSIGNED" && (
          <div className="mt-8">
            <h3 className="text-lg font-semibold mb-4 text-gray-700">
              Discussion
            </h3>
            <div className="space-y-3 mb-4">
              {replies?.map((r, idx) => {
                const isDoctor = r.user.role === "ROLE_DOCTOR";
                return (
                  <div
                    key={idx}
                    className={`max-w-lg p-3 rounded-md ${
                      isDoctor
                        ? "bg-blue-50 border border-blue-200 self-start"
                        : "bg-green-50 border border-green-200 self-end"
                    }`}
                  >
                    <p className="text-sm text-gray-700">
                      <strong className="block mb-1">
                        {r.user.firstName} {r.user.lastName}
                      </strong>
                      {r.content}
                    </p>
                  </div>
                );
              })}
            </div>

            <div className="flex gap-2">
              <input
                className="flex-1 border border-gray-300 p-2 rounded"
                placeholder="Type your reply..."
                value={newReply}
                onChange={(e) => setNewReply(e.target.value)}
              />
              <button
                onClick={handleAddReply}
                className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
              >
                Send
              </button>
            </div>
          </div>
        )}

        {status === "CLOSED" || status === "CANCELED" ? (
          <div className="mt-8">
            <h3 className="text-lg font-semibold mb-4 text-gray-700">
              Discussion (Read-Only)
            </h3>
            <div className="space-y-3 mb-2">
              {replies.map((r, idx) => {
                const isDoctor = r.author === "Doctor";
                return (
                  <div
                    key={idx}
                    className={`max-w-lg p-3 rounded-md ${
                      isDoctor
                        ? "bg-blue-50 border border-blue-200"
                        : "bg-green-50 border border-green-200"
                    }`}
                  >
                    <p className="text-sm text-gray-700">
                      <strong className="block mb-1">{r.author}</strong>
                      {r.content}
                    </p>
                  </div>
                );
              })}
            </div>
            <p className="text-gray-500">This request is {status}.</p>
          </div>
        ) : null}

        {status === "Pending" && (
          <div className="mt-8">
            <p className="text-gray-500">
              This request is still pending. No discussion available yet.
            </p>
            {/* Button to 'Process'? 
                <button className="mt-4 bg-yellow-500 px-3 py-2 text-white rounded">
                  Start Processing
                </button> 
            */}
          </div>
        )}
      </div>
    </div>
  );
}
