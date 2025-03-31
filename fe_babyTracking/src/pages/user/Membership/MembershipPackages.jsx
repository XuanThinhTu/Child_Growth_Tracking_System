import React, { useState, useEffect } from "react";
import { FaCheckCircle } from "react-icons/fa";
import { getMembershipPackages } from "../../../services/APIServices";
import { purchaseMembership } from "../../../services/membershipServices"; // <-- import hàm purchase

const MembershipPackages = () => {
  const [packages, setPackages] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchPackages = async () => {
      try {
        const result = await getMembershipPackages();
        if (result?.success) {
          setPackages(result.data || []);
        } else {
          console.error(result?.message || "Không thể lấy gói membership");
        }
      } catch (error) {
        console.error("Lỗi khi gọi API lấy gói membership:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchPackages();
  }, []);

  const handlePurchasePackage = async (packageId) => {
    try {
      const result = await purchaseMembership(packageId);
      if (result) {
        // Chuyển hướng sang trang thanh toán
        window.location.href = result.paymentUrl;
      }
    } catch (error) {
      console.log("Lỗi khi mua gói membership:", error);
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center bg-gray-100 px-4 py-12 md:py-20">
        <p>Đang tải gói Membership...</p>
      </div>
    );
  }

  return (
    <div className="flex justify-center items-center bg-green-50 px-4 py-12 md:py-20">
      <div className="max-w-[95%] lg:max-w-[1300px] w-full p-10 md:p-14 bg-white rounded-3xl shadow-lg border border-gray-300">
        {/* Title */}
        <div className="text-center">
          <span className="text-xs md:text-sm bg-green-200 text-green-700 px-3 py-1 rounded-full">
            Our Pricing Plans
          </span>
          <h1 className="text-xl md:text-2xl font-bold text-gray-900 mt-3">
            Our pricing is simple with no hidden fees
          </h1>
          <p className="text-gray-500 text-xs md:text-sm mt-2">
            7 Days free trial. No credit card required.
          </p>
        </div>

        {/* Pricing Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-10 mt-10">
          {packages.map((pkg) => (
            <div
              key={pkg.id}
              className={`p-8 md:p-10 rounded-3xl shadow-lg flex flex-col w-full ${
                pkg.featured
                  ? "bg-gradient-to-r from-green-400 to-green-500 text-white"
                  : "bg-green-100 hover:scale-105 transition-transform duration-300"
              }`}
            >
              <div className="flex flex-col md:flex-row justify-between items-center w-full">
                {/* Header Section */}
                <div className="w-full md:w-1/2 flex flex-col items-start">
                  <span
                    className={`text-xs md:text-sm ${
                      pkg.featured
                        ? "bg-white text-green-600"
                        : "bg-green-300 text-gray-700"
                    } px-3 py-1 rounded-full font-medium`}
                  >
                    {pkg.featured ? "FEATURED" : "STANDARD"}
                  </span>
                  <h3 className="text-4xl md:text-5xl font-bold mt-4">
                    ${pkg.price}
                  </h3>
                  <p className="text-sm md:text-base mt-2">
                    {pkg.billingCycle}
                  </p>
                </div>

                {/* Vertical Divider */}
                <div className="hidden md:block w-px bg-black h-24 mx-6"></div>

                {/* Features Section */}
                <ul className="w-full md:w-1/2 space-y-3 text-sm md:text-base">
                  {pkg.description ? (
                    <li>{pkg.description}</li>
                  ) : (
                    <li className="text-gray-400 italic">No features listed</li>
                  )}
                </ul>
              </div>

              {/* Button inside Card */}
              <div className="mt-6">
                <button
                  onClick={() => handlePurchasePackage(pkg.id)} // <-- gọi hàm
                  className={`w-full px-6 py-4 font-semibold rounded-full text-white transition-all ${
                    pkg.featured
                      ? "bg-green-700 hover:bg-green-800"
                      : "bg-green-600 hover:bg-green-500"
                  }`}
                >
                  {pkg.featured ? "Start for free" : "Purchase"}
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default MembershipPackages;
