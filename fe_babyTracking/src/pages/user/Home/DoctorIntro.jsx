import React from "react";

const DoctorIntro = () => {
    return (
        <div
            className="relative w-full h-[80vh] bg-cover bg-center"
            style={{
                backgroundImage: `url("https://media.licdn.com/dms/image/v2/D4E12AQG10KcV6JVCdw/article-cover_image-shrink_720_1280/article-cover_image-shrink_720_1280/0/1732154002844?e=2147483647&v=beta&t=2-KwpjzIMaTZwGcqr48JOZp3HM02lBLDjPIzQFw392c")`,
            }}
        >
            {/* Overlay mờ để text nổi bật hơn (tùy chọn) */}
            <div className="absolute inset-0 bg-black/20"></div>

            {/* Khối nội dung đè lên background */}
            <div className="relative z-10 h-full max-w-6xl mx-auto px-6 flex items-center justify-end">
                {/* Nội dung bên phải */}
                <div className="text-white text-right max-w-md">
                    <h1 className="text-4xl md:text-5xl font-bold mb-4">
                        Introduction to Baby Tracking System
                    </h1>
                    <p className="text-xl md:text-2xl mb-6">
                        Become the Doctor You Want to Have
                    </p>
                    <button
                        className="bg-blue-500 hover:bg-blue-600 text-white py-3 px-6 rounded"
                        onClick={() => (window.location.href = "/doctor")}
                    >
                        Read more
                    </button>
                </div>
            </div>
        </div>
    );
};

export default DoctorIntro;
