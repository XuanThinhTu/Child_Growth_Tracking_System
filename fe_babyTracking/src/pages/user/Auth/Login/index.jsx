import React from "react";
import logBg from "../../../../assets/images/background/log-in-bg.png";
import loginImg from "../../../../assets/images/auth/login.png";
import LoginForm from "./LoginForm";
import Breadcrumbs from "../../../../components/elements/Breadcrumb";

const SignIn = () => {
  return (
    <>
      <Breadcrumbs headline="Log In" />
      <div
        className={
          "w-full bg-cover bg-center flex flex-col lg:flex-row gap-8 my-4"
        }
        style={{ backgroundImage: `url(${logBg})` }}
      >
        <div className="lg:w-3/5 hidden lg:block">
          <div className="flex items-center justify-center w-full h-full">
            <img src={loginImg} className="w-[80%] h-auto" />
          </div>
        </div>
        <div className="lg:w-2/5 w-full flex items-center justify-center">
          <LoginForm />
        </div>
      </div>
    </>
  );
};

export default SignIn;
