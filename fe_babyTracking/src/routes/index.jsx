import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  useLocation,
  Navigate,
} from "react-router-dom";
import toast from "react-hot-toast";
import App from "../App";
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
            <Route path="/my-family" element={<MyFamily />} />
            <Route path="/baby-details/:babyId" element={<BabyOverview />} />
            <Route path="/add-baby-info/:babyId" element={<AddBabyInfo />} />
            <Route path="/booking-meeting/:babyId" element={<BookingPage />} />
            <Route path="/paypal/success" element={<PaymentCallback />} />
            <Route
              path="/consultation-request/:babyId"
              element={<ConsultationRequest />}
            />
            <Route
              path="/consultation-detail/:id"
              element={<ConsultationDetail />}
            />
            <Route path="/doctor" element={<DoctorPage />} />
            <Route path="/doctor/:doctorId" element={<DoctorDetail />} />
            <Route path="/faq" element={<FAQPage />} />
            <Route path="/blog" element={<Blog />} />
            <Route path="/blog/:id" element={<BlogDetail />} />
          </Routes>
        </div>
      ) : (
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/membership" element={<MembershipPage />} />
        </Routes>
      )}
      <MainFooter />
    </>
  );
}

const ProtectedRoute = ({ children, requiredRole }) => {
  const role = sessionStorage.getItem("role");
  if (role !== requiredRole) {
    toast.error("Access denied!");
    return <Navigate to="/" replace />;
  }
  return children;
};

function AdminLayout() {
  return (
    <ProtectedRoute requiredRole="ROLE_ADMIN">
      <div className="w-full min-h-screen bg-gray-100">
        <Routes>
          <Route path="/" element={<AdminHomePage />} />
        </Routes>
      </div>
    </ProtectedRoute>
  );
}

function DoctorLayout() {
  return (
    <ProtectedRoute requiredRole="ROLE_DOCTOR">
      <div className="w-full min-h-screen bg-gray-100">
        <Routes>
          <Route path="/" element={<DoctorDashboard />} />
        </Routes>
      </div>
    </ProtectedRoute>
  );
}

function AppRouter() {
  return (
    <Router>
      <Routes>
        <Route path="/*" element={<UserLayout />} />
        <Route path="/admin" element={<AdminLayout />} />
        <Route path="/doctor-dashboard" element={<DoctorLayout />} />
      </Routes>
    </Router>
  );
}

export default AppRouter;
