import React, { useEffect } from "react";
import Loading from "../../../../components/Loading/Loading";
import { useLocation, useNavigate } from "react-router-dom";
import { verifyEmail } from "../../../../services/APIServices";
import toast from "react-hot-toast";

const VerifyAccount = () => {
  const navigate = useNavigate();
  const location = useLocation();
  useEffect(() => {
    const handleConfirmEmail = async () => {
      const paramUrl = new URLSearchParams(location.search);
      const tokenParam = paramUrl.get("token");
      try {
        const response = await verifyEmail(tokenParam);
        if (response) {
          toast.success("Thành công!");
          navigate("/login");
        } else {
          toast.error("Thất bại!");
        }
      } catch (error) {}
    };
    handleConfirmEmail();
  }, [location.search]);
  return (
    <div className="flex items-center justify-center h-screen">
      <Loading />
    </div>
  );
};

export default VerifyAccount;
