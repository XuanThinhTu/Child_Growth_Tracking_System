import { useNavigate } from "react-router-dom";
import { useState } from "react";
import LinkToGoogle from "../Google/LinkToGoogle";
import { registerFunction } from "../../../../services/APIServices";
import toast from "react-hot-toast";

const RegisterForm = () => {
  const navigate = useNavigate();
  const [formValues, setFormValues] = useState({
    email: "",
    password: "",
    firstName: "",
    lastName: "",
    phone: "",
    address: "",
  });

  // Basic email regex
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormValues({
      ...formValues,
      [name]: value,
    });
  };

  const handleRegister = async (event) => {
    event.preventDefault();

    // 1. Validate inputs
    if (!formValues.lastName.trim()) {
      toast.error("Last name is required!");
      return;
    }
    if (!formValues.firstName.trim()) {
      toast.error("First name is required!");
      return;
    }
    if (!formValues.email.trim()) {
      toast.error("Email is required!");
      return;
    }
    if (!emailRegex.test(formValues.email)) {
      toast.error("Invalid email format!");
      return;
    }
    if (!formValues.phone.trim()) {
      toast.error("Phone number is required!");
      return;
    }
    if (!formValues.address.trim()) {
      toast.error("Address is required!");
      return;
    }
    if (!formValues.password.trim()) {
      toast.error("Password is required!");
      return;
    }

    try {
      // 2. Call registerFunction
      const result = await registerFunction(
        formValues.email,
        formValues.password,
        formValues.firstName,
        formValues.lastName,
        formValues.phone,
        formValues.address
      );

      if (result.success) {
        toast.success("A verification email has been sent. Please check your inbox!");
      } else {
        toast.error(result.message || "Registration failed!");
      }
    } catch (error) {
      if (error.response && error.response.status === 409) {
        toast.error("Email or phone number already in use!");
      } else {
        toast.error("Registration failed! " + (error.message || ""));
      }
    }
  };

  return (
    <div className="w-full px-10 py-12 rounded-3xl border-solid border-2 border-[rgba(0,0,0,0.1)] shadow-2xl">
      <h1 className="text-5xl font-semibold">Welcome</h1>
      <p className="font-medium text-lg text-gray-500 mt-4">
        Please fill in your details!
      </p>

      <form className="mt-8" onSubmit={handleRegister}>
        <div className="w-full flex gap-3">
          {/* Last Name */}
          <div className="w-1/2 flex flex-col">
            <label className="text-lg font-medium">Last Name</label>
            <input
              name="lastName"
              onChange={handleChange}
              value={formValues.lastName}
              className="w-full border-2 border-[rgba(0,0,0,0.2)] rounded-xl p-4 mt-1 bg-transparent"
              placeholder="Enter your last name..."
            />
          </div>
          {/* First Name */}
          <div className="w-1/2 flex flex-col">
            <label className="text-lg font-medium">First Name</label>
            <input
              name="firstName"
              onChange={handleChange}
              value={formValues.firstName}
              className="w-full border-2 border-[rgba(0,0,0,0.2)] rounded-xl p-4 mt-1 bg-transparent"
              placeholder="Enter your first name..."
            />
          </div>
        </div>

        {/* Email */}
        <div className="flex flex-col mt-4">
          <label className="text-lg font-medium">Email Address</label>
          <input
            name="email"
            onChange={handleChange}
            value={formValues.email}
            className="w-full border-2 rounded-xl p-4 mt-1 bg-transparent border-[rgba(0,0,0,0.2)]"
            placeholder="Enter your email..."
            type="email"
          />
        </div>

        {/* Phone */}
        <div className="flex flex-col mt-4">
          <label className="text-lg font-medium">Phone Number</label>
          <input
            name="phone"
            onChange={handleChange}
            value={formValues.phone}
            className="w-full border-2 rounded-xl p-4 mt-1 bg-transparent border-[rgba(0,0,0,0.2)]"
            placeholder="Enter your phone number..."
          />
        </div>

        {/* Address */}
        <div className="flex flex-col mt-4">
          <label className="text-lg font-medium">Address</label>
          <input
            name="address"
            onChange={handleChange}
            value={formValues.address}
            className="w-full border-2 rounded-xl p-4 mt-1 bg-transparent border-[rgba(0,0,0,0.2)]"
            placeholder="Enter your address..."
          />
        </div>

        {/* Password */}
        <div className="flex flex-col mt-4">
          <label className="text-lg font-medium">Password</label>
          <input
            name="password"
            onChange={handleChange}
            value={formValues.password}
            className="w-full border-2 border-[rgba(0,0,0,0.2)] rounded-xl p-4 mt-1 bg-transparent"
            placeholder="Enter your password..."
            type="password"
          />
        </div>

        {/* Register Button */}
        <div className="mt-8 flex flex-col gap-y-4">
          <button
            type="submit"
            className="py-4 bg-violet-500 rounded-xl text-white font-bold text-lg"
          >
            Register
          </button>
          <div>
            <LinkToGoogle headline="Sign in with Google" />
          </div>
        </div>

        {/* Link to Login */}
        <div className="mt-8 flex justify-center items-center">
          <p className="font-medium text-base">Already have an account?</p>
          <a
            href="/login"
            className="ml-2 font-medium text-base text-violet-500"
          >
            Login
          </a>
        </div>
      </form>
    </div>
  );
};

export default RegisterForm;
