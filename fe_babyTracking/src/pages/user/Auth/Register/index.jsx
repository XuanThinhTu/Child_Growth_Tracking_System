import React from "react";
import logBg from "../../../../assets/images/background/log-in-bg.png";
import siginUpImg from "../../../../assets/images/auth/sign-up.png";
import RegisterForm from "./RegisterForm";
import Breadcrumbs from "../../../../components/elements/Breadcrumb";

const SignUp = () => {
    return (
        <>
            <Breadcrumbs headline="Register" />
            <div
                className={
                    "w-full bg-cover bg-center flex flex-col lg:flex-row gap-8 my-4"
                }
                style={{ backgroundImage: `url(${logBg})` }}
            >
                <div className="lg:w-3/5 hidden lg:block">
                    <div className="flex items-center justify-center w-full h-full">
                        <img src={siginUpImg} className="w-[80%] h-auto" />
                    </div>
                </div>
                <div className="lg:w-2/5 w-full flex items-center justify-center">
                    <RegisterForm />
                </div>
            </div>
        </>
    );
};

export default SignUp;
