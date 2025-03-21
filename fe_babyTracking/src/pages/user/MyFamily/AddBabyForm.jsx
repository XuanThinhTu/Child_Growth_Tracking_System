import React, { useEffect, useState } from "react";
import { FaCamera, FaTimes, FaCalendarAlt } from "react-icons/fa";
import { addNewBaby } from "../../../services/APIServices";
import toast from "react-hot-toast";
import dayjs from "dayjs";

const BabyForm = ({ onClose }) => {
  const [newChild, setNewChild] = useState({
    name: "",
    birthday: "",
    gender: "",
    avatar: "",
  });

  // Lấy ngày hiện tại (định dạng YYYY-MM-DD) để gán cho max attribute
  const today = dayjs().format("YYYY-MM-DD");

  useEffect(() => {
    console.log(newChild);
  }, [newChild]);

  const handleImageUpload = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setNewChild({ ...newChild, avatar: reader.result });
      };
      reader.readAsDataURL(file);
    }
  };

  const handleAddBaby = async () => {
    // 1. Kiểm tra đã chọn ngày chưa
    if (!newChild.birthday) {
      toast.error("Please select a birthday!");
      return;
    }
    // 2. Kiểm tra ngày sinh có ở tương lai không
    if (dayjs(newChild.birthday).isAfter(dayjs(), "day")) {
      toast.error("Birthday cannot be in the future!");
      return;
    }
    // 3. Gọi API
    try {
      const result = await addNewBaby(
        newChild.name,
        newChild.birthday,
        newChild.gender
      );
      if (result) {
        toast.success("Add baby success!");
        setNewChild({ name: "", birthday: "", gender: "", avatar: "" });
        onClose();
      } else {
        toast.error("Add baby failed!");
      }
    } catch (error) {
      console.log(error);
      toast.error("An error occurred while adding baby.");
    }
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-white text-black p-6 rounded-xl shadow-lg w-96 relative">
        <button
          className="absolute top-4 right-4 text-gray-400 hover:text-gray-600"
          onClick={onClose}
        >
          <FaTimes size={18} />
        </button>

        <h3 className="text-lg font-semibold">My child</h3>
        <p className="text-sm text-gray-500">
          A photo helps you personalize your baby's account
        </p>

        <div className="flex justify-center my-4">
          <label className="relative cursor-pointer">
            {newChild?.avatar ? (
              <img
                src={newChild.avatar}
                alt="Avatar"
                className="w-16 h-16 rounded-full object-cover"
              />
            ) : (
              <div className="w-16 h-16 bg-green-200 rounded-full flex items-center justify-center">
                😊
              </div>
            )}
            <div className="absolute bottom-0 right-0 bg-blue-500 p-1 rounded-full">
              <FaCamera size={14} className="text-white" />
            </div>
            <input
              type="file"
              className="hidden"
              accept="image/*"
              onChange={handleImageUpload}
            />
          </label>
        </div>

        {/* Ngày sinh: thêm max={today} để ngăn chọn ngày tương lai */}
        <div className="relative">
          <input
            type="date"
            max={today}
            value={newChild.birthday}
            className="w-full p-2 pl-10 rounded-md bg-gray-100 text-black border border-gray-300 focus:outline-none"
            onChange={(e) =>
              setNewChild({ ...newChild, birthday: e.target.value })
            }
          />
          <FaCalendarAlt className="absolute left-3 top-3 text-gray-400" />
        </div>

        <input
          type="text"
          placeholder="Baby’s name"
          value={newChild.name}
          className="w-full p-2 mt-3 rounded-md bg-gray-100 text-black border border-gray-300 focus:outline-none"
          onChange={(e) => setNewChild({ ...newChild, name: e.target.value })}
        />

        <p className="mt-3 text-sm">Baby’s sex</p>
        <div className="flex space-x-3 mt-2">
          <label className="flex items-center space-x-1">
            <input
              type="radio"
              name="gender"
              value="Girl"
              checked={newChild.gender === "Girl"}
              onChange={(e) =>
                setNewChild({ ...newChild, gender: e.target.value })
              }
            />
            <span>Girl</span>
          </label>
          <label className="flex items-center space-x-1">
            <input
              type="radio"
              name="gender"
              value="Boy"
              checked={newChild.gender === "Boy"}
              onChange={(e) =>
                setNewChild({ ...newChild, gender: e.target.value })
              }
            />
            <span>Boy</span>
          </label>
        </div>

        <button
          onClick={handleAddBaby}
          className="mt-5 w-full bg-blue-500 text-white py-2 rounded-md hover:bg-blue-600 transition"
        >
          Add to my family
        </button>
      </div>
    </div>
  );
};

export default BabyForm;
