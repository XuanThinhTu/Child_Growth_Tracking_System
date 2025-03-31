import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { Button, Spin } from "antd"; // Import Ant Design Spinner
import {
  getUserInformation,
  loginFucntion,
  getMyMembershipPackage,
} from "../../../../services/APIServices";
import toast from "react-hot-toast";
import LinkToGoogle from "../Google/LinkToGoogle";

const LoginForm = () => {
  const navigation = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  const fetchLogin = async () => {
    if (!email) {
      toast.error("Email is required!");
      return;
    }
    if (!emailRegex.test(email)) {
      toast.error("Invalid email format!");
      return;
    }
    if (!password) {
      toast.error("Password is required!");
      return;
    }

    setLoading(true); // Bắt đầu loading
    try {
      const result = await loginFucntion(email, password);
      const token = result?.data?.accessToken;

      if (token) {
        // Save token to session
        sessionStorage.setItem("token", token);
        const userInfo = await getUserInformation();
        sessionStorage.setItem("userId", userInfo.data?.id);
        sessionStorage.setItem("role", userInfo.data?.role);
        toast.success("Login success!");

        if (userInfo.data?.role === "ROLE_ADMIN") {
          navigation("/admin");
        } else if (userInfo.data?.role === "ROLE_DOCTOR") {
          navigation("/doctor-dashboard");
        } else {
          try {
            const membershipRes = await getMyMembershipPackage();
            if (membershipRes.success && membershipRes.data) {
              if (membershipRes.data.status === "AVAILABLE") {
                navigation("/");
              } else {
                navigation("/membership");
              }
            } else {
              navigation("/membership");
            }
          } catch (err) {
            console.log("Error checking membership:", err);
            navigation("/membership");
          }
        }
      }
    } catch (error) {
      toast.error(error?.message || "Login failed");
    }
    setLoading(false); // Dừng loading
  };

  return (
    <div className="w-full px-10 py-12 rounded-3xl border-solid border-2 border-[rgba(0,0,0,0.1)] shadow-2xl">
      <h1 className="text-5xl font-semibold">Welcome Back</h1>
      <p className="font-medium text-lg text-gray-500 mt-4">
        Please enter your information!
      </p>

      <div className="mt-8">
        <form>
          <div className="flex flex-col">
            <label className="text-lg font-medium">Email Address</label>
            <input
              className="w-full border-2 border-[rgba(0,0,0,0.2)] rounded-xl p-4 mt-1 bg-transparent"
              type="email"
              placeholder="Enter your email..."
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div className="flex flex-col mt-4">
            <label className="text-lg font-medium">Password</label>
            <input
              className="w-full border-2 border-[rgba(0,0,0,0.2)] rounded-xl p-4 mt-1 bg-transparent"
              type="password"
              placeholder="Enter your password..."
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          <div className="mt-8 flex justify-between items-center">
            <div>
              <input type="checkbox" id="remember" />
              <label className="ml-2 font-medium text-base" htmlFor="remember">
                Remember me
              </label>
            </div>
            <a
              className="font-medium text-base text-violet-500"
              href="/forgot-password"
            >
              Forgot your password?
            </a>
          </div>

          <div className="mt-8 flex flex-col gap-y-4">
            <Button
              type="button"
              size="large"
              loading={loading}
              disabled={loading}
              className="bg-green-500 w-full h-14 rounded-xl text-white font-bold text-lg flex items-center justify-center"
              onClick={fetchLogin}
            >
              {loading ? <span className="ml-2">Logging in...</span> : "Log In"}
            </Button>

            <div>
              <LinkToGoogle headline="Log in with Google" />
            </div>
          </div>
        </form>

        <div className="mt-8 flex justify-center items-center">
          <p className="font-medium text-base">Don't have an account?</p>
          <a
            href="/register"
            className="ml-2 font-medium text-base text-green-500"
          >
            Sign Up
          </a>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
