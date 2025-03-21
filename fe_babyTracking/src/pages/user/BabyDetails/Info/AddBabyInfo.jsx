import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  addBabyGrowthData,
  getBabyInfo,
} from "../../../../services/APIServices";
import toast from "react-hot-toast";

const AddBabyInfo = () => {
  const { babyId } = useParams();
  const navigate = useNavigate();
  const [baby, setBaby] = useState(null);
  const [babyData, setBabyData] = useState({
    weight: "",
    height: "",
    headCircumference: "",
    date: new Date().toISOString().split("T")[0],
  });

  const handleChange = (e) => {
    setBabyData({ ...babyData, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    const fetchBabyInfo = async () => {
      try {
        const result = await getBabyInfo(babyId);
        setBaby(result);
      } catch (error) {
        console.log(error);
      }
    };
    fetchBabyInfo();
  }, []);

  const handleAddGrowthData = async (e) => {
    e.preventDefault();
    try {
      const result = await addBabyGrowthData(
        babyId,
        babyData.height,
        babyData.weight,
        babyData.headCircumference,
        babyData.date
      );
      setBabyData({
        height: "",
        weight: "",
        headCircumference: "",
        date: new Date().toISOString().split("T")[0],
      });
      navigate(`/baby-details/${babyId}`);
      toast.success("Thêm chỉ số thành công!");
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      <div>
        {/* Header */}
        <div className="flex justify-between items-center mb-8">
          <div></div> {/* Giữ không gian để tiêu đề căn giữa */}
          <h2 className="text-2xl font-bold text-purple-800 text-center">
            Thêm chỉ số cho bé
          </h2>
          <a href="#" className="text-purple-600 text-lg hover:underline">
            Tất cả chỉ số
          </a>
        </div>
      </div>
      <div className="max-w-4xl mx-auto p-8 bg-white shadow-lg rounded-lg">
        {/* Form */}
        <form onSubmit={handleAddGrowthData} className="grid grid-cols-2 gap-6">
          {/* Bé */}
          <div className="col-span-2">
            <label className="block text-purple-700 font-medium mb-2">Bé</label>
            <div className="flex items-center bg-gray-100 p-4 rounded-lg">
              <img
                src="https://via.placeholder.com/50"
                alt="Baby Avatar"
                className="w-12 h-12 rounded-full mr-4"
              />
              <span className="text-gray-800 font-medium text-lg">
                {baby?.name}
              </span>
            </div>
          </div>

          {/* Cân nặng */}
          <div>
            <label className="block text-purple-700 font-medium mb-2">
              Cân nặng
            </label>
            <div className="flex items-center border rounded-lg overflow-hidden">
              <input
                type="number"
                name="weight"
                value={babyData.weight}
                onChange={handleChange}
                placeholder="Nhập cân nặng"
                className="w-full p-4 outline-none text-lg"
              />
              <span className="bg-gray-100 px-5 py-4 text-gray-700 text-lg">
                kg
              </span>
            </div>
          </div>

          {/* Chiều cao */}
          <div>
            <label className="block text-purple-700 font-medium mb-2">
              Chiều cao
            </label>
            <div className="flex items-center border rounded-lg overflow-hidden">
              <input
                type="number"
                name="height"
                value={babyData.height}
                onChange={handleChange}
                placeholder="Nhập chiều cao"
                className="w-full p-4 outline-none text-lg"
              />
              <span className="bg-gray-100 px-5 py-4 text-gray-700 text-lg">
                cm
              </span>
            </div>
          </div>

          {/* Chu vi đầu */}
          <div>
            <label className="block text-purple-700 font-medium mb-2">
              Chu vi đầu
            </label>
            <div className="flex items-center border rounded-lg overflow-hidden">
              <input
                type="number"
                name="headCircumference"
                value={babyData.headCircumference}
                onChange={handleChange}
                placeholder="Nhập chu vi đầu"
                className="w-full p-4 outline-none text-lg"
              />
              <span className="bg-gray-100 px-5 py-4 text-gray-700 text-lg">
                cm
              </span>
            </div>
          </div>

          {/* Ngày */}
          <div>
            <label className="block text-purple-700 font-medium mb-2">
              Ngày
            </label>
            <div className="flex items-center border rounded-lg overflow-hidden bg-gray-100">
              <span className="px-4 text-xl">📅</span>
              <input
                type="date"
                name="date"
                value={babyData.date}
                onChange={handleChange}
                className="w-full p-4 outline-none bg-gray-100 text-lg"
              />
            </div>
          </div>

          {/* Nút Thêm (cả hàng) */}
          <div className="col-span-2">
            <button
              type="submit"
              className="w-full bg-gradient-to-r from-purple-600 to-purple-800 text-white py-4 rounded-lg text-xl font-semibold mt-4 hover:shadow-md transition"
            >
              Thêm
            </button>
          </div>
        </form>
      </div>
    </>
  );
};

export default AddBabyInfo;
