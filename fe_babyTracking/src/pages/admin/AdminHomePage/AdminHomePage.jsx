import {
  AreaChartOutlined,
  BarChartOutlined,
  CalendarOutlined,
  LaptopOutlined,
  MailOutlined,
  PieChartOutlined,
  ProfileOutlined,
  ScheduleOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { Avatar, Dropdown, Layout, Menu, Space } from "antd";
import { Content, Footer, Header } from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";
import { useEffect, useState } from "react";
import UserManagement from "../content/UserManagement";
import DoctorManagement from "../content/DoctorManagement";
import AppointmentManagement from "../content/AppointmentManagement";
import Standard from "../content/Standard";
import Overview from "../content/Overview";
import { useNavigate } from "react-router-dom";
import AccountManagement from "../content/AccountManagement";
import { getUserInformation } from "../../../services/APIServices";
import Schedule from "../content/Schedule";
import Consultation from "../content/Consultation";

const items1 = [
  { key: "home", label: "Home" },
  { key: "users", label: "Users" },
];

const items2 = [
  {
    key: "dashboard",
    icon: <LaptopOutlined />,
    label: "Dashboard",
  },
  {
    key: "account-management",
    icon: <UserOutlined />,
    label: "Account Management",
    children: [
      { key: "user-management", label: "User Accounts" },
      { key: "doctor-management", label: "Doctor Accounts" },
    ],
  },
  {
    key: "standard",
    icon: <ProfileOutlined />,
    label: "Growth Standard",
  },
  {
    key: "consultation",
    icon: <MailOutlined />,
    label: "Consultation",
  },
  {
    key: "appointment",
    icon: <CalendarOutlined />,
    label: "Appointment",
  },
  {
    key: "work-schedule",
    icon: <ScheduleOutlined />,
    label: "Work Schedule ",
  },
];

function AdminHomePage() {
  const [selectedKey, setSelectedKey] = useState("dashboard");
  const navigator = useNavigate();
  const [user, setUser] = useState(null);
  const token = sessionStorage.getItem("token");

  const handleProfileClick = (e) => {
    if (e.key === "logout") {
      navigator("/login");
    }
  };

  const profileMenu = (
    <Menu
      onClick={handleProfileClick}
      items={[
        { key: "profile", label: "Profile" },
        { key: "logout", label: "Logout" },
      ]}
    />
  );

  const renderContent = () => {
    switch (selectedKey) {
      case "user-management":
        return <UserManagement />;
      case "doctor-management":
        return <DoctorManagement />;
      case "appointment":
        return <AppointmentManagement />;
      case "standard":
        return <Standard />;
      case "consultation":
        return <Consultation />;
      case "dashboard":
        return <Overview />;
      case "users":
        return <AccountManagement />;
      case "work-schedule":
        return <Schedule />;
    }
  };

  useEffect(() => {
    const fetchUserInfo = async () => {
      if (!token) return;
      try {
        const result = await getUserInformation();
        if (result?.data) {
          setUser(result.data);
        }
      } catch (error) {
        console.error("Failed to fetch user info:", error);
      }
    };

    fetchUserInfo();
  }, [token]);

  return (
    <Layout style={{ height: "100vh", width: "100vw", overflow: "hidden" }}>
      <Header
        style={{ display: "flex", alignItems: "center", background: "white" }}
      >
        <div className="demo-logo" />
        <Menu
          theme="light"
          mode="horizontal"
          defaultSelectedKeys={["home"]}
          items={items1}
          style={{ flex: 1 }}
          onClick={({ key }) => setSelectedKey(key)}
        />

        <Dropdown overlay={profileMenu} trigger={["click"]}>
          <Space>
            <span>Welcome, {user?.firstName}!</span>
            <Avatar
              size="small"
              icon={<UserOutlined />}
              style={{
                cursor: "pointer",
                display: "flex",
              }}
            />
          </Space>
        </Dropdown>
      </Header>

      <Layout style={{ flex: 1 }}>
        <Sider width={200} style={{ height: "100vh", background: "#fff" }}>
          <Menu
            mode="inline"
            defaultSelectedKeys={["1"]}
            defaultOpenKeys={["account-management"]}
            style={{ height: "100%" }}
            items={items2}
            onClick={({ key }) => setSelectedKey(key)}
          />
        </Sider>

        <Content
          style={{
            flex: 1,
            padding: "20px",
            background: "#f0f2f5",
            overflow: "auto",
          }}
        >
          {renderContent()}
        </Content>
      </Layout>

      <Footer
        style={{
          textAlign: "center",
          background: "#fff",
          padding: "10px 0",
          position: "fixed",
          bottom: 0,
          left: 0,
          width: "100%",
          boxShadow: "0 -2px 5px rgba(0,0,0,0.1)",
        }}
      >
        © 2025 Baby Tracking. All rights reserved.
      </Footer>
    </Layout>
  );
}

export default AdminHomePage;
