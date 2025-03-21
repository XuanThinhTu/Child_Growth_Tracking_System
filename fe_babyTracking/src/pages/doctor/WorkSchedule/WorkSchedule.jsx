import { useEffect, useState } from "react";
import { CheckCircleIcon } from "@heroicons/react/solid";
import {
  getNewWorkingShiftDraft,
  getUserInformation,
  registerWorkingShift,
  submitWorkingShift,
} from "../../../services/APIServices";
import dayjs from "dayjs";
import toast from "react-hot-toast";

export default function WorkSchedule() {
  const [shifts, setShifts] = useState([
    { id: 1, title: "Shift A", status: "Draft" },
    { id: 2, title: "Shift B", status: "Approved" },
    { id: 3, title: "Shift C", status: "Rejected" },
  ]);

  const [showForm, setShowForm] = useState(false);
  const [currentStatus, setCurrentStatus] = useState("");

  const [fromDate, setFromDate] = useState("");
  const [toDate, setToDate] = useState("");
  const [userInfo, setUserInfo] = useState(null);
  const [scheduleData, setScheduleData] = useState([]);
  const today = new Date().toISOString().split("T")[0];
  const [numberOfShift, setNumberOfShifts] = useState(0);

  const handleCreate = () => {
    setShowForm(true);
    setScheduleData([]);
    setCurrentStatus("");
    setFromDate("");
    setToDate("");
  };

  const countTrueValues = (data) => {
    return data.map(
      (item) =>
        Object.values(item.shifts).filter((value) => value === true).length
    );
  };

  const handleSave = async () => {
    try {
      if (currentStatus !== "Draft") {
        const result = await registerWorkingShift(scheduleData);
        if (result) {
          setNumberOfShifts(countTrueValues(scheduleData));
          toast.success("Bạn đã lưu bản nháp đăng ký ca làm thành công!");
          setCurrentStatus("Draft");
        } else {
          toast.error("Bản nháp ca làm chưa được đăng ký");
        }
      } else {
        setCurrentStatus("");
      }
    } catch (error) {
      console.log(error);
    }
  };

  const handleDiscard = () => {
    setShowForm(false);
  };

  const toggleCheck = (index, shiftType) => {
    setScheduleData((prevSchedule) =>
      prevSchedule.map((row, idx) =>
        idx === index
          ? {
              ...row,
              shifts: { ...row.shifts, [shiftType]: !row.shifts[shiftType] },
            }
          : row
      )
    );
  };

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const result = await getUserInformation();
        setUserInfo(result.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchUserInfo();
  }, []);

  useEffect(() => {
    if (fromDate && toDate) {
      const newScheduleData = [];
      const startDate = dayjs(fromDate, "YYYY-MM-DD");
      const endDate = dayjs(toDate, "YYYY-MM-DD");

      let currentDate = startDate;

      while (currentDate.isBefore(endDate) || currentDate.isSame(endDate)) {
        newScheduleData.push({
          date: currentDate.format("YYYY-MM-DD"),
          shifts: {
            morning: false,
            afternoon: false,
            evening: false,
          },
        });

        currentDate = currentDate.add(1, "day");
      }

      setScheduleData(newScheduleData);
    } else {
      setScheduleData([]);
    }
  }, [fromDate, toDate]);

  const handleSubmit = async () => {
    try {
      const dates = scheduleData.map((item) => item.date);
      const newSchedule = await getNewWorkingShiftDraft(
        userInfo.id,
        dates,
        numberOfShift
      );
      const submittedIds = newSchedule?.map((item) =>
        item.map((item) => item.id)
      );
      const result = await submitWorkingShift(submittedIds);
      if (result) {
        setShowForm(false);
        setNumberOfShifts(0);
        toast.success("Bạn đã đăng ký ca làm thành công!");
        setScheduleData([]);
      } else {
        toast.error("Đăng ký ca làm thất bại!");
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="p-4">
      {/* HEADER + CREATE BUTTON */}
      <div className="flex items-center justify-between mb-6">
        <h2 className="text-2xl font-bold">Work Schedule</h2>
        {!showForm && (
          <button
            onClick={handleCreate}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          >
            Create
          </button>
        )}
      </div>

      {/* Nếu KHÔNG show form => hiển thị Kanban */}
      {!showForm && (
        <div className="grid grid-cols-3 gap-4">
          {/* DRAFT */}
          <div className="bg-gray-100 p-4 rounded shadow-sm">
            <h3 className="text-lg font-semibold mb-2">Draft</h3>
            <div className="space-y-2">
              {shifts
                .filter((s) => s.status === "Draft")
                .map((s) => (
                  <div key={s.id} className="bg-white p-2 rounded shadow-sm">
                    {s.title}
                  </div>
                ))}
            </div>
          </div>

          {/* APPROVED */}
          <div className="bg-gray-100 p-4 rounded shadow-sm">
            <h3 className="text-lg font-semibold mb-2">Approved</h3>
            <div className="space-y-2">
              {shifts
                .filter((s) => s.status === "Approved")
                .map((s) => (
                  <div key={s.id} className="bg-white p-2 rounded shadow-sm">
                    {s.title}
                  </div>
                ))}
            </div>
          </div>

          {/* REJECTED */}
          <div className="bg-gray-100 p-4 rounded shadow-sm">
            <h3 className="text-lg font-semibold mb-2">Rejected</h3>
            <div className="space-y-2">
              {shifts
                .filter((s) => s.status === "Rejected")
                .map((s) => (
                  <div key={s.id} className="bg-white p-2 rounded shadow-sm">
                    {s.title}
                  </div>
                ))}
            </div>
          </div>
        </div>
      )}

      {/* Nếu showForm => hiển thị FORM */}
      {showForm && (
        <div className="space-y-6">
          {/* STATUS TRACKER */}
          <StatusTracker currentStatus={currentStatus} />

          {/* FORM chia 2 phần */}
          <div className="grid grid-cols-2 gap-6">
            {/* 1/2 trái */}
            <div className="space-y-4">
              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  Từ ngày
                </label>
                <input
                  type="date"
                  value={fromDate}
                  min={today}
                  onChange={(e) => setFromDate(e.target.value)}
                  className="w-full border border-gray-300 rounded p-2"
                />
              </div>

              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  Tới ngày
                </label>
                <input
                  type="date"
                  value={toDate}
                  min={today}
                  onChange={(e) => setToDate(e.target.value)}
                  className="w-full border border-gray-300 rounded p-2"
                />
              </div>

              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  Các ca làm đã đăng kí trong tháng
                </label>
                <div className="bg-gray-50 p-3 rounded text-gray-600">
                  20 buổi
                </div>
              </div>
            </div>

            {/* 1/2 phải */}
            <div className="space-y-4">
              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  Tên bác sĩ
                </label>
                <div className="bg-gray-50 p-3 rounded text-gray-600">
                  Dr. {userInfo?.firstName} {userInfo?.lastName}
                </div>
                <p className="text-sm text-gray-500">{userInfo?.email}</p>
              </div>

              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  Admin
                </label>
                <div className="bg-gray-50 p-3 rounded text-gray-600">
                  Tu X. Thinh
                </div>
              </div>

              <div>
                <label className="block text-gray-700 font-medium mb-1">
                  Company
                </label>
                <div className="bg-gray-50 p-3 rounded text-gray-600">
                  BabyTracking Inc.
                </div>
              </div>
            </div>
          </div>

          {/* TABLE LỊCH CA LÀM */}
          <div>
            <h4 className="text-lg font-semibold mb-2">Lịch đăng ký ca</h4>
            <table className="w-full text-left">
              <thead>
                <tr className="bg-gray-100">
                  <th className="p-2">Date</th>
                  <th className="p-2">Ca Sáng</th>
                  <th className="p-2">Ca Chiều</th>
                  <th className="p-2">Ca Tối</th>
                </tr>
              </thead>
              <tbody>
                {fromDate && toDate ? (
                  <>
                    {scheduleData.map((row, idx) => (
                      <tr
                        key={idx}
                        className={idx % 2 === 0 ? "bg-white" : "bg-gray-50"}
                      >
                        <td className="p-2">{row.date}</td>
                        <td className="p-2">
                          <input
                            type="checkbox"
                            checked={row?.shifts.morning}
                            onChange={() => toggleCheck(idx, "morning")}
                            disabled={currentStatus === "Draft"}
                          />
                        </td>
                        <td className="p-2">
                          <input
                            type="checkbox"
                            checked={row?.shifts.afternoon}
                            onChange={() => toggleCheck(idx, "afternoon")}
                            disabled={currentStatus === "Draft"}
                          />
                        </td>
                        <td className="p-2">
                          <input
                            type="checkbox"
                            checked={row?.shifts.evening}
                            onChange={() => toggleCheck(idx, "evening")}
                            disabled={currentStatus === "Draft"}
                          />
                        </td>
                      </tr>
                    ))}
                  </>
                ) : (
                  <tr>
                    <td colSpan="4" className="text-center p-4">
                      Please select your free day
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>

          {/* Nút SUBMIT */}
          <div className="flex justify-end gap-4">
            <button
              onClick={handleSubmit}
              className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
              disabled={currentStatus !== "Draft"}
            >
              Submit
            </button>
          </div>

          {/* Nút Save / Discard */}
          <div className="flex justify-end gap-4 mt-4">
            <p>Kiểm tra thông tin trước khi lưu</p>
            <button
              onClick={handleSave}
              className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
            >
              {currentStatus !== "Draft" ? "Save" : "Edit"}
            </button>
            <button
              onClick={handleDiscard}
              className="bg-gray-300 text-gray-700 px-4 py-2 rounded hover:bg-gray-400"
            >
              Discard
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

/* 
    Component StatusTracker: hiển thị 4 status: 
    Draft, Submit, Confirmed, Approved
    Phần nào trùng currentStatus => highlight
    */
function StatusTracker({ currentStatus }) {
  const steps = ["Draft", "Submit", "Confirmed", "Approved"];

  return (
    <div className="flex items-center gap-4 mb-4">
      {steps.map((st, idx) => {
        const isActive = st === currentStatus;
        return (
          <div key={st} className="flex items-center">
            {isActive ? (
              <CheckCircleIcon className="h-6 w-6 text-blue-600" />
            ) : (
              <div className="h-6 w-6 rounded-full border-2 border-gray-300" />
            )}
            <span
              className={`ml-2 font-medium ${
                isActive ? "text-blue-600" : "text-gray-500"
              }`}
            >
              {st}
            </span>
            {idx < steps.length - 1 && (
              <div className="mx-2 w-8 h-1 bg-gray-300" />
            )}
          </div>
        );
      })}
    </div>
  );
}
