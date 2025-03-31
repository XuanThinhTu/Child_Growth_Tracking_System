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
                    Children are not things to be molded, but are people to be unfolded. <span className="text-purple-600">The greatest legacy</span>.  we can give
                    our children is not things, but an understanding of themselves
                    and the world around them.{" "}
                        
                    </p>
                    <p className="text-2xl font-semibold text-gray-900">Dr. Kelly Flanagan</p>
                    <p className="text-xl text-gray-500">Nutritionist</p>
                </div>
            </div>
        </div>
    );
};

export default Testimonial;
