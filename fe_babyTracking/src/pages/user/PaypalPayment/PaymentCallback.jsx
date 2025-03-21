import React, { useEffect } from "react";
import Loading from "../../../components/Loading/Loading";
import { useLocation, useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import { paymentCallback } from "../../../services/membershipServices";

const PaymentCallback = () => {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const handlePayment = async () => {
      const paramUrl = new URLSearchParams(location.search);
      const paymentId = paramUrl.get("paymentId");
      const payerId = paramUrl.get("PayerID");
      try {
        console.log("paymentId", paymentId);
        console.log("payerId", payerId);
        const response = await paymentCallback(paymentId, payerId);
        if (response) {
          toast.success("Thanh toán thành công!");
          navigate("/");
        } else {
          toast.error("Thanh toán thất bại!");
        }
      } catch (error) {
        console.error("Error processing payment callback: ", error);
        toast.error("Thất bại!");
      }
    };
    handlePayment();
  }, [location.search, navigate]);
  return (
    <div className="flex items-center justify-center h-screen">
      <Loading />
    </div>
  );
};

export default PaymentCallback;
