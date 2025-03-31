import React, { useEffect, useState } from "react";
import { AiOutlinePlus, AiOutlineMinus } from "react-icons/ai";
import { getAllFAQs } from "../../../services/APIServices"; // Đảm bảo rằng API đã được tạo

const FAQ = () => {
  const [faqItems, setFaqItems] = useState([]);
  const [openIndex, setOpenIndex] = useState(null);

  const toggleFAQ = (index) => {
    setOpenIndex(openIndex === index ? null : index);
  };

  useEffect(() => {
    const fetchFAQData = async () => {
      try {
        const result = await getAllFAQs(); // Gọi API để lấy dữ liệu FAQ
        setFaqItems(result);
      } catch (error) {
        console.log(error);
      }
    };
    fetchFAQData();
  }, []);

  return (
    <div className="w-full flex">
      {/* Cột trái: ảnh hoặc nội dung */}
      <div className="w-1/2 p-6 border-r bg-indigo-600">
        <img
          src="https://static.vecteezy.com/system/resources/thumbnails/024/296/148/small/cute-white-baby-looking-out-of-a-bed-sheet-on-a-bed-photo.jpg"
          alt="Surprised Baby"
          className="w-full h-auto rounded shadow-md"
        />
      </div>

      {/* Cột phải: FAQ */}
      <div className="w-1/2 p-6 bg-white flex flex-col items-center">
        {/* Tiêu đề FAQ căn giữa */}
        <h2 className="text-3xl font-bold mb-6">FAQ</h2>

        {/* Danh sách các câu hỏi, chia dòng bằng divide-y */}
        <ul className="w-full divide-y divide-gray-300">
          {faqItems?.map((item, index) => {
            const isOpen = openIndex === index;
            return (
              <li key={index} className="flex items-center py-4">
                {/* Vòng tròn chứa dấu cộng hoặc trừ */}
                <div className="w-8 h-8 flex items-center justify-center border border-gray-400 rounded-full mr-4">
                  {isOpen ? (
                    <AiOutlineMinus className="text-gray-700 text-lg" />
                  ) : (
                    <AiOutlinePlus className="text-gray-700 text-lg" />
                  )}
                </div>
                <div className="flex-1">
                  <span className="text-lg text-gray-800 font-medium leading-relaxed">
                    {item.question}
                  </span>

                  {/* Hiển thị câu trả lời nếu FAQ được mở */}
                  {isOpen && item.answer && (
                    <div className="mt-2 ml-7 text-gray-700 text-sm leading-relaxed">
                      {item.answer}
                    </div>
                  )}
                </div>
              </li>
            );
          })}
        </ul>
      </div>
    </div>
  );
};

export default FAQ;
