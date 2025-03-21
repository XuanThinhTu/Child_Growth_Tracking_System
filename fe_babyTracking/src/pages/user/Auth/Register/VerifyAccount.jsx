import React, { useState } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import { toast } from "react-hot-toast";
import { verifyUser } from "../../../../services/APIServices";
// Heroicons
import { CheckCircleIcon, ExclamationIcon } from "@heroicons/react/outline";

export default function VerifyAccount() {
    const [searchParams] = useSearchParams();
    const navigate = useNavigate();
    // Lấy token từ URL
    const token = searchParams.get("token");

    const [status, setStatus] = useState("idle"); // idle | loading | success | error

    const handleActivate = async () => {
        if (!token) {
            toast.error("Không tìm thấy token!");
            return;
        }
        try {
            setStatus("loading");
            const result = await verifyUser(token);
            // result = { success, message, errorCode, userInfo }
            if (result?.success) {
                setStatus("success");
                toast.success("Tài khoản đăng kí thành công!");
                // chuyển sang /login sau 2s, hoặc tuỳ ý
                setTimeout(() => {
                    navigate("/login");
                }, 2000);
            } else {
                toast.error(result?.message || "Xác thực thất bại!");
                setStatus("error");
            }
        } catch (error) {
            console.error(error);
            toast.error(error.message || "Có lỗi xảy ra!");
            setStatus("error");
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-green-50 via-green-100 to-green-200 p-6">
            <div className="max-w-md w-full bg-white p-8 rounded-md shadow relative overflow-hidden">
                {/* Decor shape */}
                <div
                    className="absolute -top-16 -right-16 w-40 h-40 bg-green-300 rounded-full opacity-30"
                    style={{ filter: "blur(80px)" }}
                ></div>

                {/* Header */}
                <div className="mb-6 text-center">
                    <h1 className="text-3xl font-bold text-green-700">Xác thực tài khoản</h1>
                    <p className="text-gray-500 mt-1">
                        Cảm ơn bạn đã đăng ký! Hãy hoàn tất xác thực để bắt đầu.
                    </p>
                </div>

                {!token ? (
                    <div className="flex flex-col items-center text-green-500">
                        <ExclamationIcon className="h-10 w-10 mb-2" />
                        <p className="font-semibold">Không có token trong URL!</p>
                    </div>
                ) : (
                    <div className="text-center">
                        {status === "success" ? (
                            <div className="flex flex-col items-center text-green-500 my-6">
                                <CheckCircleIcon className="h-10 w-10 mb-2" />
                                <p className="font-semibold">Tài khoản đã được xác thực!</p>
                                <p className="text-gray-500 text-sm mt-1">
                                    Đang chuyển hướng sang trang đăng nhập...
                                </p>
                            </div>
                        ) : (
                            <button
                                onClick={handleActivate}
                                disabled={status === "loading"}
                                className="inline-block w-full bg-green-600 text-white py-3 rounded hover:bg-green-700 transition font-semibold"
                            >
                                {status === "loading" ? "Đang xử lý..." : "Activate Account"}
                            </button>
                        )}
                        {status === "error" && (
                            <p className="mt-4 text-green-500 font-medium">
                                Có lỗi xảy ra, vui lòng thử lại!
                            </p>
                        )}
                    </div>
                )}
            </div>
        </div>
    );
}
