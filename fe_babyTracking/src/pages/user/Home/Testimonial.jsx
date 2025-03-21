import React from "react";

const Testimonial = () => {
    return (
        <div className="max-w-7xl mx-auto px-8 py-12">
            <div className="flex items-start">
                {/* Dấu ngoặc kép thật to */}
                <div className="mr-6 text-indigo-600 text-[10rem] leading-none font-serif select-none">
                    “
                </div>

                {/* Nội dung trích dẫn */}
                <div>
                    <p className="text-4xl text-gray-800 mb-8 leading-relaxed">
                        Et eirmod et dignissim quo, ne pro dicat adolescens. His te velit
                        doler nemorate integre. Est zril id, ad legere persius inciderint
                        pri, vel et nemore{" "}
                        <span className="text-purple-600">facete virtute</span>.
                    </p>
                    <p className="text-2xl font-semibold text-gray-900">Jake Chaps</p>
                    <p className="text-xl text-gray-500">Nutritionist</p>
                </div>
            </div>
        </div>
    );
};

export default Testimonial;
