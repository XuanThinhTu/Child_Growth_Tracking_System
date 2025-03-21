import React, { useEffect, useState } from "react";
import { getMembershipPackages } from "../../../services/APIServices";
import { CheckIcon } from "@heroicons/react/outline";
import { purchaseMembership } from "../../../services/membershipServices";

export default function MembershipPage() {
  const [packages, setPackages] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchPackages() {
      try {
        const result = await getMembershipPackages();
        if (result?.success) {
          setPackages(result.data || []);
        }
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    }
    fetchPackages();
  }, []);

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-100">
        <p className="text-gray-600">Đang tải gói Membership...</p>
      </div>
    );
  }

  const handlePurchasePackage = async (packageId) => {
    try {
      const result = await purchaseMembership(packageId);
      if (result) {
        window.location.href = result?.paymentUrl;
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="relative isolate min-h-screen bg-gradient-to-tr from-green-50 via-green-100 to-green-200 px-6 py-24 sm:py-32 lg:px-8">
      {/* Decor Shape (optional) */}
      <div
        aria-hidden="true"
        className="absolute inset-x-0 top-0 -z-10 transform-gpu overflow-hidden blur-3xl"
      >
        <div
          className="mx-auto aspect-square w-[80rem] rounded-full bg-gradient-to-r from-green-200 via-green-300 to-green-400 opacity-40"
          style={{
            clipPath:
              "polygon(70% 0%, 100% 35%, 100% 100%, 30% 100%, 0% 65%, 0% 0%)",
          }}
        />
      </div>

      {/* Heading */}
      <div className="mx-auto max-w-4xl text-center">
        <h2 className="text-base font-semibold text-green-600">
          Membership Packages
        </h2>
        <p className="mt-2 text-5xl font-semibold tracking-tight text-gray-900 sm:text-6xl">
          Choose the Right Plan for You
        </p>
      </div>

      <p className="mx-auto mt-6 max-w-2xl text-center text-lg font-medium text-gray-700 sm:text-xl">
        No hidden fees. Get the best features to support your goals.
      </p>

      {/* Hiển thị packages (3 cột) */}
      <div className="mx-auto mt-16 grid max-w-5xl grid-cols-1 gap-12 items-stretch sm:mt-20 lg:grid-cols-2">
        {packages.length === 0 ? (
          <p className="text-center col-span-3 text-gray-600">
            No membership packages available.
          </p>
        ) : (
          packages.map((pkg) => (
            <div
              key={pkg.id}
              className={`flex flex-col justify-between 
                rounded-3xl p-10 shadow 
                min-h-[500px]
                ${pkg.featured
                  ? "bg-gradient-to-r from-green-400 via-green-500 to-green-300 text-white shadow-2xl"
                  : "bg-white/70 backdrop-blur-md ring-1 ring-gray-900/10"
                }`}
            >
              {/* Title */}
              <h3
                id={`package-${pkg.id}`}
                className={
                  pkg.featured
                    ? "text-green-100 text-base font-semibold"
                    : "text-green-600 text-base font-semibold"
                }
              >
                {pkg.name}
              </h3>

              {/* Price */}
              <p className="mt-4 flex items-baseline gap-x-2">
                <span
                  className={
                    pkg.featured
                      ? "text-5xl font-semibold tracking-tight text-white"
                      : "text-5xl font-semibold tracking-tight text-gray-900"
                  }
                >
                  ${pkg.price || 0}
                </span>
                <span
                  className={
                    pkg.featured
                      ? "text-green-100 text-base"
                      : "text-gray-500 text-base"
                  }
                >
                  /month
                </span>
              </p>

              {/* Description */}
              <p
                className={
                  pkg.featured
                    ? "mt-6 text-base text-green-100"
                    : "mt-6 text-base text-gray-600"
                }
              >
                {pkg.description || "No description"}
              </p>

              {/* Feature list */}
              <ul
                role="list"
                className={
                  pkg.featured
                    ? "mt-8 space-y-3 text-sm text-green-100"
                    : "mt-8 space-y-3 text-sm text-gray-600"
                }
              >
                {pkg.features?.length ? (
                  pkg.features.map((feature) => (
                    <li key={feature} className="flex gap-x-3">
                      <CheckIcon
                        aria-hidden="true"
                        className={
                          pkg.featured
                            ? "h-6 w-5 flex-none text-green-100"
                            : "h-6 w-5 flex-none text-green-600"
                        }
                      />
                      {feature}
                    </li>
                  ))
                ) : (
                  <li className="text-gray-400 italic">No features listed</li>
                )}
              </ul>

              {/* CTA button */}
              <button
                className={`mt-8 block w-full rounded-md px-3.5 py-2.5 text-center text-sm font-semibold shadow-md focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 
                ${pkg.featured
                    ? "bg-white text-green-700 hover:bg-green-50 focus-visible:outline-green-500"
                    : "bg-green-500 text-white hover:bg-green-400 focus-visible:outline-green-500"
                  }
                `}
                onClick={() => handlePurchasePackage(pkg.id)}
              >
                Buy now
              </button>
            </div>
          ))
        )}
      </div>
    </div>
  );
}
