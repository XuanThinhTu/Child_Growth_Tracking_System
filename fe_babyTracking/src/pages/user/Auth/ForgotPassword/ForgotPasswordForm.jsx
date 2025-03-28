import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const ForgotPasswordForm = () => {

  return (
    <div className="w-full px-10 py-12 rounded-3xl border-solid border-2 border-[rgba(0,0,0,0.1)] shadow-2xl">
      <p className="font-medium text-2xl text-gray-500 mt-4">
        Vui lòng kiểm tra mail sau khi xác nhận!
      </p>

      <div className="mt-8">
        <form>
          <div className="flex flex-col">
            <label className="text-lg font-medium">Địa Chỉ Email</label>
            <input
              className="w-full border-2 rounded-xl p-4 mt-1 bg-transparent border-[rgba(0,0,0,0.2)]"
              placeholder="Nhập email..."
              disabled
            />
          </div>

          <div className="mt-8 flex flex-col gap-y-4">
            <button
              type="button"
              className="cursor-not-allowed py-4 bg-violet-500 rounded-xl text-white font-bold text-lg"
              disabled
            >
              Xác Nhận
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ForgotPasswordForm;
