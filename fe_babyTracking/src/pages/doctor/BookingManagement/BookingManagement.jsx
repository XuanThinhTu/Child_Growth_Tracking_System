import React, { useEffect, useState } from "react";
import {
  cancelMeeting,
  closeMeeting,
  getMeetingInfo,
  startMeeting,
} from "../../../services/APIServices";
import toast from "react-hot-toast";

const BookingManagement = () => {
  const [meetingList, setMeetingList] = useState([]);

  useEffect(() => {
    const fetchBookingList = async () => {
      try {
        const result = await getMeetingInfo();
        setMeetingList(result);
      } catch (error) {
        console.log(error);
      }
    };
    fetchBookingList();
  }, []);

  const handleStartMeeting = async (meeting) => {
    try {
      const result = await startMeeting(meeting?.id);
      if (result) {
        toast.success("Start meeting success!");
        window.location.href = meeting?.meetingLink;
      } else {
        toast.error("Start meeting failed!");
      }
    } catch (error) {
      console.log(error);
    }
  };

  const handleCloseMeeting = async (meetingId) => {
    try {
      const result = await closeMeeting(meetingId);
      if (result) {
        toast.success("Close meeting success!");
      } else {
        toast.error("Close meeting failed!");
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="p-4">
      <h2 className="text-2xl font-bold mb-4">Booking Management</h2>
      <table className="w-full border-collapse">
        <thead>
          <tr className="bg-gray-100 border-b">
            <th className="text-left p-2">Id</th>
            <th className="text-left p-2">Parent</th>
            <th className="text-left p-2">Requested Date</th>
            <th className="text-left p-2">Time</th>
            <th className="text-left p-2">Note</th>
            <th className="text-left p-2">Status</th>
            <th className="text-left p-2">Action</th>
            <th className="p-2"></th>
          </tr>
        </thead>
        <tbody>
          {meetingList?.map((bk) => (
            <tr key={bk.id} className="border-b hover:bg-gray-50">
              <td className="p-2">{bk.id}</td>
              <td className="p-2">
                {bk.memberFirstName} {bk.memberLastName}
              </td>
              <td className="p-2">{bk.date}</td>
              <td className="p-2">
                {bk.startTime} - {bk.endTime}
              </td>
              <td className="p-2">{bk.content}</td>
              <td className="p-2">
                <span
                  className={`px-2 py-1 rounded text-white ${
                    bk.status === "PENDING"
                      ? "bg-yellow-500"
                      : bk.status === "CLOSED" || bk.status === "CANCELLED"
                      ? "bg-red-600"
                      : "bg-green-600"
                  }`}
                >
                  {bk.status}
                </span>
              </td>
              <td className="p-2">
                {bk.status === "PENDING" ? (
                  <button
                    onClick={() => handleStartMeeting(bk)}
                    className="bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700"
                  >
                    Start
                  </button>
                ) : bk.status === "PROCESSING" ? (
                  <>
                    <button
                      onClick={() => handleCloseMeeting(bk.id)}
                      className="bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700"
                    >
                      Done
                    </button>
                  </>
                ) : (
                  <></>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BookingManagement;
