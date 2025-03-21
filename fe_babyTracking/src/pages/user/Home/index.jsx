import React from "react";
import CarouselHero from "../../../components/Hero/CarouselHero";
import MembershipPage from "../Membership/index";
import BMICalculator from "./BMICalculator";
import DoctorIntro from "./DoctorIntro";
import Testimonial from "./Testimonial";
import FAQ from "./FAQ";
import MembershipPackages from "../Membership/MembershipPackages";

function HomePage() {
  const token = sessionStorage.getItem("token");
  return (
    <>
      <CarouselHero />
      <BMICalculator />
      <DoctorIntro />
      <Testimonial />
      <FAQ />
      {token && <MembershipPackages />}
    </>
  );
}

export default HomePage;
