import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getAllDoctors } from "../../../services/APIServices";
import {
  FaStar,
  FaStarHalfAlt,
  FaRegStar,
  FaUserCircle,
} from "react-icons/fa";

const DoctorDetail = () => {
  const { doctorId } = useParams();
  const [doctor, setDoctor] = useState(null);

  // Mock feedback cũ
  const [feedbacks, setFeedbacks] = useState([
    {
      user: "John",
      rating: 4.5,
      comment: "Excellent doctor, very friendly and professional.",
    },
    {
      user: "Mary",
      rating: 4,
      comment: "Good experience, but waiting time was a bit long.",
    },
  ]);

  // State cho feedback mới
  const [newRating, setNewRating] = useState(0);
  const [newComment, setNewComment] = useState("");

  useEffect(() => {
    const getDoctorInfo = async () => {
      try {
        const doctors = await getAllDoctors();
        const selectedDoctor = doctors.find(
          (doc) => doc.id === parseInt(doctorId)
        );
        setDoctor(selectedDoctor);
      } catch (error) {
        console.log(error);
      }
    };
    getDoctorInfo();
  }, [doctorId]);

  // Tính trung bình rating (nếu có feedbacks)
  const averageRating = feedbacks.length
    ? feedbacks.reduce((sum, f) => sum + f.rating, 0) / feedbacks.length
    : 0;

  // Hàm render sao dựa trên rating (hiển thị 5 sao)
  const renderStars = (rating) => {
    const stars = [];
    const fullStars = Math.floor(rating);
    const hasHalf = rating - fullStars >= 0.5;

    for (let i = 0; i < fullStars; i++) {
      stars.push(<FaStar key={`full-${i}`} className="text-yellow-400" />);
    }

    if (hasHalf) {
      stars.push(<FaStarHalfAlt key="half" className="text-yellow-400" />);
    }

    // Thêm sao rỗng để đủ 5
    while (stars.length < 5) {
      stars.push(
        <FaRegStar key={`empty-${stars.length}`} className="text-yellow-400" />
      );
    }

    return <div className="flex items-center gap-1">{stars}</div>;
  };

  // Chọn rating (khi user click vào sao)
  const handleSelectRating = (value) => {
    setNewRating(value);
  };

  // Submit feedback mới (mock)
  const handleSubmitFeedback = () => {
    if (newRating === 0 || !newComment.trim()) {
      alert("Please provide both rating and comment.");
      return;
    }
    const newFeedback = {
      user: "Current User",
      rating: newRating,
      comment: newComment,
    };
    setFeedbacks([...feedbacks, newFeedback]);
    setNewRating(0);
    setNewComment("");
  };

  if (!doctor) {
    return (
      <div className="max-w-6xl mx-auto px-4 py-8">
        <p className="text-gray-600">Loading doctor information...</p>
      </div>
    );
  }

  return (
    <div className="max-w-6xl mx-auto px-4 py-8 space-y-8">
      {/* Phần 1: Doctor Info */}
      <section className="bg-white p-6 rounded shadow-sm flex flex-col md:flex-row md:space-x-8 items-center md:items-start">
        <div className="md:w-1/3 w-full flex flex-col items-center md:items-center border p-6 rounded shadow-sm">
          <img
            src={doctor?.image || "https://via.placeholder.com/150"}
            alt="Doctor"
            className="w-36 h-36 object-cover rounded-full mb-4 shadow"
          />
          <h2 className="text-2xl font-semibold mb-1 text-gray-800">
            Dr. {doctor.firstName} {doctor.lastName}
          </h2>
          <p className="text-sm text-gray-500 mb-3">Cardiology</p>
          <p className="text-gray-600 text-sm">{doctor?.phone}</p>
          <p className="text-blue-600 text-sm">{doctor?.email}</p>
        </div>

        {/* Thông tin Rating trung bình */}
        <div className="md:w-2/3 w-full mt-6 md:mt-0">
          <h3 className="text-xl font-semibold text-gray-700 mb-2">
            Overall Rating
          </h3>
          <div className="flex items-center">
            {renderStars(averageRating)}
            <span className="ml-2 text-gray-700 font-medium">
              {averageRating.toFixed(1)} / 5.0
            </span>
            <span className="ml-2 text-sm text-gray-500">
              ({feedbacks.length} reviews)
            </span>
          </div>
        </div>
      </section>

      {/* Phần 2: Doctor Description & Details */}
      <section className="bg-white p-6 rounded shadow-sm">
        <h3 className="text-xl font-semibold text-gray-800 mb-4">About</h3>
        <p className="text-gray-700 mb-6 leading-relaxed">
          Execrt taion ullamcorper suscipit lobortis nisl ut aliquip ex ea
          commodo. Non habent claritatem insitamcon quat est usus. Eodem modo
          typi qui nunc nobis eleifend option congue nihil imperdiet doming.
          <br />
          <br />
          Lorem ipsum nunc vel risus suscipit nulla rutrum vel in ultrices enim.
          Hendrerit in vulputate velit esse molestie consequat, vel illum dolore
          eu feugiat nulla facilisis at vero eros et accumsan.
        </p>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {/* Specialty */}
          <div>
            <h4 className="text-lg font-semibold mb-1 text-gray-800">
              Specialty
            </h4>
            <p className="text-gray-700">Cardiology</p>
          </div>

          {/* Degrees */}
          <div>
            <h4 className="text-lg font-semibold mb-1 text-gray-800">
              Degrees
            </h4>
            <p className="text-gray-700">M.D. of Medicine</p>
          </div>

          {/* Training */}
          <div>
            <h4 className="text-lg font-semibold mb-1 text-gray-800">
              Training
            </h4>
            <p className="text-gray-700">
              Graduated from Harvard Medical School, specialized in advanced
              cardiac care.
            </p>
          </div>

          {/* Work Days */}
          <div>
            <h4 className="text-lg font-semibold mb-1 text-gray-800">
              Work Days
            </h4>
            <p className="text-gray-700">Mon - Fri (8:00 - 16:00)</p>
          </div>
        </div>
      </section>

      {/* Phần 3: Ratings & Feedback */}
      <section className="bg-white p-6 rounded shadow-sm">
        <h3 className="text-xl font-semibold text-gray-800 mb-4">
          User Ratings & Feedback
        </h3>

        {/* Form thêm Feedback */}
        <div className="mb-6">
          <p className="text-gray-700 mb-2">Add your rating:</p>
          <div className="flex items-center gap-2 mb-3">
            {[1, 2, 3, 4, 5].map((value) => (
              <button
                key={value}
                type="button"
                onClick={() => handleSelectRating(value)}
                className="focus:outline-none"
              >
                {value <= newRating ? (
                  <FaStar className="text-yellow-400 text-xl" />
                ) : (
                  <FaRegStar className="text-yellow-400 text-xl" />
                )}
              </button>
            ))}
            <span className="ml-2 text-gray-600">
              {newRating > 0 ? `${newRating} star(s)` : "No rating yet"}
            </span>
          </div>

          <textarea
            rows="3"
            placeholder="Write your feedback..."
            className="w-full p-2 border border-gray-300 rounded focus:outline-none"
            value={newComment}
            onChange={(e) => setNewComment(e.target.value)}
          />

          <button
            className="mt-3 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
            onClick={handleSubmitFeedback}
          >
            Submit Feedback
          </button>
        </div>

        {/* Danh sách feedback cũ */}
        <div className="space-y-4">
          {feedbacks.map((fb, index) => (
            <div key={index} className="p-4 border rounded shadow-sm">
              <div className="flex items-center gap-2 mb-2">
                <FaUserCircle className="text-gray-400 text-2xl" />
                <span className="font-semibold text-gray-800">{fb.user}</span>
                {renderStars(fb.rating)}
                <span className="text-gray-600 ml-2">{fb.rating.toFixed(1)}</span>
              </div>
              <p className="text-gray-700 italic">{fb.comment}</p>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
};

export default DoctorDetail;
