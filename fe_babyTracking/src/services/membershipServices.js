import axios from "axios";

const token = sessionStorage.getItem("token");
const userId = sessionStorage.getItem("userId");
const baseUrl = import.meta.env.VITE_API_BASE_URL;

export const getMembershipPackages = async () => {
  try {
    const response = await axios.get(`${baseUrl}/membership-package/list`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error fetching membership packages: ", error);
  }
};

export const purchaseMembership = async (packageId) => {
  try {
    const response = await axios.post(
      `${baseUrl}/membership-package/payment/${packageId}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data.data;
  } catch (error) {
    console.error("Error purchasing membership: ", error);
  }
};

export const paymentCallback = async (paymentId, PayerId) => {
  try {
    const response = await axios.get(
      `${baseUrl}/membership-package/execute?paymentId=${paymentId}&PayerID=${PayerId}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response;
  } catch (error) {
    console.error("Error processing payment callback: ", error);
  }
};
