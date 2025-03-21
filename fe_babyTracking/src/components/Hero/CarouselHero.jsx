import React, { useState, useEffect } from "react";
import { FaChevronLeft, FaChevronRight } from "react-icons/fa";

const images = [
  "https://www.mamasandpapas.ie/cdn/shop/articles/cropped-Brand_Weaning_3_1440x810_crop_center.jpg?v=1602071300",
  "https://images.ctfassets.net/6m9bd13t776q/1ZrwJJgadUFW8LOMKTLepe/01766d7715bf1377106a6393004eeecb/BWBW-week-6-3776703.png?fm=webp&q=75&w=780",
  "https://cdn-azure.notinoimg.com/cdn-cgi/image/w=1040,q=80/blog/article/01_8e1a76.jpg",
  "https://t3.ftcdn.net/jpg/00/17/30/74/360_F_17307408_RcdYtwlTOMmQAqqqYLZkJBDgb1SKHOXZ.jpg",
  "https://i1.adis.ws/i/canon/get-inspired-baby-photos-hero-16x9_0ce4f439ed9d42d2aeb0d2d162c9ac32?$media-collection-full-dt-jpg$",
];

function CarouselHero() {
  const [currentIndex, setCurrentIndex] = useState(0);

  // Tự động chuyển slide mỗi 10 giây
  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
    }, 10000);
    return () => clearInterval(interval);
  }, []);

  const nextSlide = () => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
  };

  const prevSlide = () => {
    setCurrentIndex(
      (prevIndex) => (prevIndex - 1 + images.length) % images.length
    );
  };

  return (
    <div className="relative w-full h-screen overflow-hidden">
      {/* Vùng chứa các ảnh */}
      <div className="relative w-full h-full">
        {images.map((image, index) => (
          <div
            key={index}
            className={`absolute inset-0 transition-opacity duration-700 ${index === currentIndex ? "opacity-100 z-10" : "opacity-0"
              }`}
          >
            <img
              src={image}
              alt={`Slide ${index + 1}`}
              className="w-full h-full object-cover"
            />
          </div>
        ))}
      </div>

      {/* Bỏ overlay mờ nếu không muốn che hình
          <div className="absolute inset-0 bg-black/30 z-20" /> */}

      {/* Text overlay (nội dung bên trái, nhích sang phải bằng ml-16) */}
      <div className="absolute inset-0 flex items-center z-30 px-8 ml-16">
        <div className="max-w-lg text-white text-left">
          <h1 className="text-4xl md:text-5xl font-bold mb-4">
            Supporting Every Step, Celebrating Every Growth
          </h1>
          <p className="text-lg md:text-xl mb-6">
            BabyTracking helps parents easily monitor their child's development, ensuring each milestone is met with care and confidence.
          </p>
          <button className="bg-pink-500 hover:bg-pink-600 text-white py-2 px-4 rounded">
            READ MORE
          </button>
        </div>
      </div>

      {/* Nút chuyển slide trái/phải */}
      <button
        onClick={prevSlide}
        className="absolute top-1/2 left-4 transform -translate-y-1/2 z-40 flex items-center justify-center h-12 w-12 bg-black/50 hover:bg-black/70 rounded-full shadow-md"
      >
        <FaChevronLeft className="w-6 h-6 text-white" />
      </button>
      <button
        onClick={nextSlide}
        className="absolute top-1/2 right-4 transform -translate-y-1/2 z-40 flex items-center justify-center h-12 w-12 bg-black/50 hover:bg-black/70 rounded-full shadow-md"
      >
        <FaChevronRight className="w-6 h-6 text-white" />
      </button>
    </div>
  );
}

export default CarouselHero;
