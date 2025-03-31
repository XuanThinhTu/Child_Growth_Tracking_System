import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  useLocation,
  Navigate,
} from "react-router-dom";
import toast from "react-hot-toast";
import AdminHomePage from "../pages/admin/AdminHomePage/AdminHomePage";
import HomePage from "../pages/user/Home/index";
import SignIn from "../pages/user/Auth/Login";
import UserHeader from "../components/Header/UserHeader";
import SignUp from "../pages/user/Auth/Register";
import ForgotPassword from "../pages/user/Auth/ForgotPassword";
import MainFooter from "../components/Footer";
import MyFamily from "../pages/user/MyFamily";
import BabyOverview from "../pages/user/BabyDetails";
import AddBabyInfo from "../pages/user/BabyDetails/Info/AddBabyInfo";
import ConsultationRequest from "../pages/user/BabyDetails/Consultation/index";
import ConsultationDetail from "../pages/user/BabyDetails/Consultation/ConsultationDetail";
import BookingPage from "../pages/user/Calendar";
import DoctorPage from "../pages/user/Doctor";
import DoctorDetail from "../pages/user/Doctor/DoctorDetail";
import DoctorDashboard from "../pages/doctor";
import MembershipPage from "../pages/user/Membership/index";
import FAQPage from "../pages/user/FAQ/FAQPage";
import VerifyAccount from "../pages/user/Auth/Register/VerifyAccount";
import PaymentCallback from "../pages/user/PaypalPayment/PaymentCallback";
import Blog from "../pages/user/Blog/Blog";
import BlogDetail from "../pages/user/Blog/BlogDetail";

const AuthMiddleware = ({ children, requiredRole }) => {
  const role = sessionStorage.getItem("role");
  const token = sessionStorage.getItem("token");

  if (!token) {
    toast.error("Vui lòng đăng nhập!");
    return <Navigate to="/login" replace />;
  }

  if (requiredRole && role !== requiredRole) {
    toast.error("Access denied!");
    sessionStorage.clear();
    return <Navigate to="/login" replace />;
  }

  return children;
};

function UserLayout() {
  const location = useLocation();

  // Gom tất cả prefix cần check vào 1 mảng
  const contentPaths = [
    "/login",
    "/register",
    "/verify",
    "/forgot-password",
    "/my-family",
    "/faq",
    "/add-baby-info",
    "/consultation-request",
    "/booking-meeting",
    "/doctor",
    "/baby-details/",
    "/add-baby-info/",
    "/doctor/",
    "/consultation-detail/",
    "/paypal/success",
    "/blog",
    "/blog/",
  ];

  const isContentPage = contentPaths.some((path) =>
    location.pathname.startsWith(path)
  );

  return (
    <>
      <UserHeader />
      {isContentPage ? (
        <div className="container mx-auto px-2 py-4 pt-[70px]">
          <Routes>
            <Route path="/login" element={<SignIn />} />
            <Route path="/register" element={<SignUp />} />
            <Route path="/verify" element={<VerifyAccount />} />
            <Route path="/forgot-password" element={<ForgotPassword />} />
            <Route
              path="/my-family"
              element={
                <AuthMiddleware requiredRole="ROLE_USER">
                  <MyFamily />
                </AuthMiddleware>
              }
            />
            <Route
              path="/baby-details/:babyId"
              element={
                <AuthMiddleware requiredRole="ROLE_USER">
                  <BabyOverview />
                </AuthMiddleware>
              }
            />
            <Route
              path="/add-baby-info/:babyId"
              element={
                <AuthMiddleware requiredRole="ROLE_USER">
                  <AddBabyInfo />
                </AuthMiddleware>
              }
            />
            <Route
              path="/booking-meeting/:babyId"
              element={
                <AuthMiddleware requiredRole="ROLE_USER">
                  <BookingPage />
                </AuthMiddleware>
              }
            />
            <Route path="/paypal/success" element={<PaymentCallback />} />
            <Route
              path="/consultation-request/:babyId"
              element={
                <AuthMiddleware requiredRole="ROLE_USER">
                  <ConsultationRequest />
                </AuthMiddleware>
              }
            />
            <Route
              path="/consultation-detail/:id"
              element={
                <AuthMiddleware requiredRole="ROLE_USER">
                  <ConsultationDetail />
                </AuthMiddleware>
              }
            />
            <Route
              path="/doctor"
              element={
                <AuthMiddleware requiredRole="ROLE_USER">
                  <DoctorPage />
                </AuthMiddleware>
              }
            />
            <Route
              path="/doctor/:doctorId"
              element={
                <AuthMiddleware requiredRole="ROLE_USER">
                  <DoctorDetail />
                </AuthMiddleware>
              }
            />
            <Route
              path="/faq"
              element={
                <AuthMiddleware requiredRole="ROLE_USER">
                  <FAQPage />
                </AuthMiddleware>
              }
            />
            <Route
              path="/blog"
              element={
                <AuthMiddleware requiredRole="ROLE_USER">
                  <Blog />
                </AuthMiddleware>
              }
            />
            <Route
              path="/blog/:id"
              element={
                <AuthMiddleware requiredRole="ROLE_USER">
                  <BlogDetail />
                </AuthMiddleware>
              }
            />
          </Routes>
        </div>
      ) : (
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route
            path="/membership"
            element={
              <AuthMiddleware requiredRole="ROLE_USER">
                <MembershipPage />
              </AuthMiddleware>
            }
          />
        </Routes>
      )}
      <MainFooter />
    </>
  );
}

function AdminLayout() {
  return (
    <div className="w-full min-h-screen bg-gray-100">
      <Routes>
        <Route path="/" element={<AdminHomePage />} />
      </Routes>
    </div>
  );
}

function DoctorLayout() {
  return (
    <div className="w-full min-h-screen bg-gray-100">
      <Routes>
        <Route path="/" element={<DoctorDashboard />} />
      </Routes>
    </div>
  );
}

function AppRouter() {
  return (
    <Router>
      <Routes>
        <Route path="/*" element={<UserLayout />} />
        <Route
          path="/admin"
          element={
            <AuthMiddleware requiredRole="ROLE_ADMIN">
              <AdminLayout />
            </AuthMiddleware>
          }
        />
        <Route
          path="/doctor-dashboard"
          element={
            <AuthMiddleware requiredRole="ROLE_DOCTOR">
              <DoctorLayout />
            </AuthMiddleware>
          }
        />
      </Routes>
    </Router>
  );
}

export default AppRouter;
