import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { FaPlus, FaEdit } from "react-icons/fa";
import EditBabyForm from "./EditBabyForm";
import BabyForm from "./AddBabyForm";
import Breadcrumbs from "../../../components/elements/Breadcrumb";
import { getAllBabies } from "../../../services/APIServices";
import dayjs from "dayjs";
import { Spin } from "antd";
import { LoadingOutlined } from "@ant-design/icons";

const MyFamily = () => {
  const [babies, setBabies] = useState([]);
  const [editingBaby, setEditingBaby] = useState(null);
  const [isAdding, setIsAdding] = useState(false);
  const [loading, setLoading] = useState(true);

  const fetchBabies = async () => {
    try {
      const babiesData = await getAllBabies();
      setBabies(babiesData.data);
    } catch (error) {
      console.log(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBabies();
  }, []);

  const handleRefreshBabies = () => {
    setLoading(true);
    fetchBabies();
  };

  return (
    <>
      <Breadcrumbs headline="My Family" />
      <div className="w-full min-h-screen flex flex-col items-center bg-white text-black p-8">
        <h1 className="text-2xl font-bold mb-6">I'm a parent</h1>
        <div className="w-full max-w-3xl bg-gray-100 p-6 rounded-2xl shadow-md">
          {loading ? (
            <Spin
              indicator={<LoadingOutlined spin />}
              size="large"
              style={{ display: "flex", justifyContent: "center" }}
            />
          ) : (
            <>
              {babies.length === 0 ? (
                <div className="flex flex-col items-center justify-center p-6">
                  <p className="text-gray-600 text-lg mb-4">
                    No baby found. Please add a child to get started.
                  </p>
                  <button
                    className="flex items-center space-x-2 text-blue-500 hover:text-blue-700 border border-blue-500 rounded-lg px-4 py-2 transition"
                    onClick={() => setIsAdding(true)}
                  >
                    <FaPlus />
                    <span>Add a child</span>
                  </button>
                </div>
              ) : (
                <>
                  {babies.map((baby) => (
                    <div
                      key={baby.id}
                      className="flex justify-between items-center p-4 border-b border-gray-300"
                    >
                      <Link
                        to={`/baby-details/${baby.id}`}
                        className="flex items-center space-x-4 cursor-pointer hover:bg-gray-100 p-3 rounded-lg transition"
                      >
                        <div>
                          <p className="text-lg font-semibold">{baby.name}</p>
                          <p className="text-gray-500">
                            {dayjs(baby.birthDate).format("DD/MM/YYYY")}
                          </p>
                          <p className="text-gray-500">Gender: {baby.gender}</p>
                        </div>
                      </Link>
                      <button
                        className="p-2 text-blue-500 hover:text-blue-700"
                        onClick={() => setEditingBaby(baby)}
                      >
                        <FaEdit size={20} />
                      </button>
                    </div>
                  ))}
                  <button
                    className="flex items-center space-x-2 text-blue-500 mt-4 hover:text-blue-700"
                    onClick={() => setIsAdding(true)}
                  >
                    <FaPlus />
                    <span>Add a child</span>
                  </button>
                </>
              )}
            </>
          )}
        </div>

        {editingBaby && (
          <EditBabyForm
            baby={editingBaby}
            onClose={() => setEditingBaby(null)}
            onSuccess={handleRefreshBabies}
          />
        )}

        {isAdding && (
          <BabyForm
            onClose={() => setIsAdding(false)}
            onSuccess={handleRefreshBabies}
          />
        )}
      </div>
    </>
  );
};

export default MyFamily;
