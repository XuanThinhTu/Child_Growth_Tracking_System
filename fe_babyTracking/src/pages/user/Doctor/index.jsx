import { useEffect, useState } from "react";
import { AiOutlinePlus } from "react-icons/ai";
import { useNavigate } from "react-router-dom";
import Breadcrumbs from "../../../components/elements/Breadcrumb";
import { getAllDoctors } from "../../../services/APIServices";

const DoctorPage = () => {
  const [doctors, setDoctors] = useState([]);
  const navigate = useNavigate();
  useEffect(() => {
    const fetchAllDoctors = async () => {
      try {
        const result = await getAllDoctors();
        setDoctors(result);
      } catch (error) {
        console.log(error);
      }
    };
    fetchAllDoctors();
  }, []);

  return (
    <>
      <Breadcrumbs headline="Our Doctors" />
      <div className="w-full py-8">
        {/* Lưới 6 card: 2 hàng x 3 cột trên màn hình md trở lên */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {doctors?.map((doc) => (
            <div
              key={doc.id}
              className="bg-white shadow-md rounded-lg overflow-hidden group relative"
            >
              {/* Container cho ảnh */}
              <div className="relative w-full h-80 overflow-hidden">
                <img
                  src={doc.image}
                  alt={doc.name}
                  className="absolute inset-0 w-full h-full object-cover transition-transform duration-300 transform group-hover:scale-105"
                />
              </div>

              {/* Thông tin bác sĩ */}
              <div className="p-4 flex flex-col items-center text-center">
                <h2 className="text-xl font-semibold">
                  {doc.firstName} {doc.lastName}
                </h2>
                <p className="text-gray-500">{doc.email}</p>
              </div>

              {/* Biểu tượng dấu cộng */}
              <button
                className="absolute bottom-4 right-4 bg-white p-2 rounded-full shadow group-hover:bg-blue-100 transition-colors"
                onClick={() => navigate(`/doctor/${doc.id}`)}
              >
                <AiOutlinePlus className="text-xl text-gray-600" />
              </button>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default DoctorPage;
