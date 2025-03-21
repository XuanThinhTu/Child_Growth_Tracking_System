import axios from "axios";
import dayjs from "dayjs";

const token = sessionStorage.getItem("token");
const userId = sessionStorage.getItem("userId");
const baseUrl = import.meta.env.VITE_API_BASE_URL;

export const loginFucntion = async (mail, pass) => {
  try {
    const loginData = {
      email: mail,
      password: pass,
    };
    const response = await axios.post(`${baseUrl}/auth/login`, loginData);
    return response.data;
  } catch (error) {
    if (error.response) {
      throw new Error(error.response.data.message);
    } else {
      throw new Error("Some thing when wrong!");
    }
  }
};

export const registerFunction = async (
  email,
  password,
  firstName,
  lastName,
  phone,
  address
) => {
  try {
    const regisData = {
      email: email,
      password: password,
      firstName: firstName,
      lastName: lastName,
      phone: phone,
      address: address,
    };
    const result = await axios.post(`${baseUrl}/user/register`, regisData, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    console.log(result.data);
    return result.data;
  } catch (error) {
    console.log(error);
  }
};

export async function verifyUser(tokenParam) {
  try {
    // Gọi POST /user/verify với body { token }
    const response = await axios.post(
      `${baseUrl}/user/verify`,
      { token: tokenParam },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    const { success, message, errorCode, data } = response.data;

    // Tách thông tin user
    const userInfo = {
      id: data?.id,
      email: data?.email,
      firstName: data?.firstName,
      lastName: data?.lastName,
      phone: data?.phone,
      address: data?.address,
      avatar: data?.avatar,
      role: data?.role,
      active: data?.active,
      banned: data?.banned,
    };

    // Trả về object gọn gàng
    return {
      success,
      message,
      errorCode,
      userInfo,
    };
  } catch (error) {
    // Bắt lỗi, lấy message từ backend (nếu có)
    if (error.response) {
      throw new Error(error.response.data.message || "Xác thực thất bại!");
    } else {
      throw new Error("Something went wrong!");
    }
  }
}

export const getUserInformation = async () => {
  try {
    const token = sessionStorage.getItem("token");
    const result = await axios.get(`${baseUrl}/user/p`, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });
    return result.data;
  } catch (error) {
    console.log(error);
    return null;
  }
};

// Current Membership
export const getMyMembershipPackage = async () => {
  try {
    const token = sessionStorage.getItem("token");
    const result = await axios.get(`${baseUrl}/membership-package/my-package`, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });
    return result.data;
  } catch (error) {
    console.log(error);
    return null;
  }
};

export const getAllBabies = async () => {
  try {
    const result = await axios.get(`${baseUrl}/children/list`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return result.data;
  } catch (error) {
    console.log("API Call Error:", error);
  }
};

export const getBabyInfo = async (babyId) => {
  try {
    const result = await axios.get(`${baseUrl}/children/info/${babyId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return result.data.data;
  } catch (error) {
    console.log(error);
  }
};

export const addNewBaby = async (babyName, birthday, gender) => {
  try {
    const result = await axios.post(
      `${baseUrl}/children/add?name=${babyName}&birthDate=${birthday}&gender=${gender}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }
    );
    return result.data;
  } catch (error) {
    console.log(error);
  }
};

export const updateBabyProfile = async (babyId, name, birthday, gender) => {
  try {
    const result = await axios.put(
      `${baseUrl}/children/update/${babyId}?name=${name}&birthDate=${birthday}&gender=${gender}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }
    );
    return result.data;
  } catch (error) {
    console.log(error);
  }
};

export const deleteBaby = async (babyId) => {
  try {
    const result = await axios.delete(`${baseUrl}/children/delete/${babyId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return result.data;
  } catch (error) {
    console.log(error);
  }
};
