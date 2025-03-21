import React from "react";
import { AiOutlinePlus } from "react-icons/ai";

const FAQ = () => {
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
                    <li className="flex items-center py-4">
                        {/* Vòng tròn chứa dấu cộng */}
                        <div className="w-8 h-8 flex items-center justify-center border border-gray-400 rounded-full mr-4">
                            <AiOutlinePlus className="text-gray-700 text-lg" />
                        </div>
                        <span className="text-lg text-gray-800 font-medium leading-relaxed">
                            Discover how the foods you eat impact your overall health and the quality of your life.
                        </span>
                    </li>
                    <li className="flex items-center py-4">
                        <div className="w-8 h-8 flex items-center justify-center border border-gray-400 rounded-full mr-4">
                            <AiOutlinePlus className="text-gray-700 text-lg" />
                        </div>
                        <span className="text-lg text-gray-800 font-medium leading-relaxed">
                            Our specialists will help you achieve your goals.
                        </span>
                    </li>
                    <li className="flex items-center py-4">
                        <div className="w-8 h-8 flex items-center justify-center border border-gray-400 rounded-full mr-4">
                            <AiOutlinePlus className="text-gray-700 text-lg" />
                        </div>
                        <span className="text-lg text-gray-800 font-medium leading-relaxed">
                            How to plan a healthy diet plan.
                        </span>
                    </li>
                    <li className="flex items-center py-4">
                        <div className="w-8 h-8 flex items-center justify-center border border-gray-400 rounded-full mr-4">
                            <AiOutlinePlus className="text-gray-700 text-lg" />
                        </div>
                        <span className="text-lg text-gray-800 font-medium leading-relaxed">
                            Tailor-made nutrition plans for all of our patients.
                        </span>
                    </li>
                </ul>
            </div>
        </div>
    );
};

export default FAQ;
