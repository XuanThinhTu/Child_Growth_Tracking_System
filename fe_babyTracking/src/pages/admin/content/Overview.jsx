import React, { useEffect, useState } from "react";
import {
  Card,
  Row,
  Col,
  Statistic,
  Table,
  Button,
  List,
  Avatar,
  Flex,
  Typography,
  Splitter,
} from "antd";
import {
  UserOutlined,
  SolutionOutlined,
  CalendarOutlined,
  ProfileOutlined,
  HeartOutlined,
  FileTextOutlined,
} from "@ant-design/icons";
import { BarChart, PieChart } from "@mui/x-charts";
import {
  getAllChildren,
  getAllConsultations,
  getAllUserAccounts,
  getApprovedList,
} from "../../../services/APIServices";

const Overview = () => {
  const [users, setUsers] = useState(0);
  const [doctors, setDoctors] = useState(0);
  const [consultations, setConsultations] = useState(0);
  const [babies, setBabies] = useState(0);
  const [workSchedules, setWorkSchedules] = useState(0);

  useEffect(() => {
    const fetchAccounts = async () => {
      try {
        const result = await getAllUserAccounts();
        setUsers(result.data.filter((acc) => acc.role === "ROLE_USER").length);
        setDoctors(
          result.data.filter((acc) => acc.role === "ROLE_DOCTOR").length
        );
      } catch (error) {
        console.log(error);
      }
    };
    fetchAccounts();
  }, [users, doctors]);

  useEffect(() => {
    const fetchConsultations = async () => {
      try {
        const result = await getAllConsultations();
        setConsultations(result.length);
      } catch (error) {
        console.log(error);
      }
    };
    fetchConsultations();
  }, [consultations]);

  useEffect(() => {
    const fetchBabies = async () => {
      try {
        const result = await getAllChildren();
        setBabies(result.data.content.length);
      } catch (error) {
        console.log(error);
      }
    };
    fetchBabies();
  }, [babies]);

  useEffect(() => {
    const fetchWorkSchedules = async () => {
      try {
        const result = await getApprovedList();
        setWorkSchedules(result.length);
      } catch (error) {
        console.log(error);
      }
    };
    fetchWorkSchedules();
  }, [workSchedules]);

  const stats = [
    { title: "Total Babies", value: babies, icon: <UserOutlined /> },
    { title: "Total Users", value: users, icon: <SolutionOutlined /> },
    { title: "Total Doctors", value: doctors, icon: <ProfileOutlined /> },
    {
      title: "Consultations",
      value: consultations,
      icon: <SolutionOutlined />,
    },
    {
      title: "Work Slots",
      value: workSchedules,
      icon: <CalendarOutlined />,
    },
  ];

  const columns = [
    { title: "User", dataIndex: "name", key: "name" },
    { title: "Activity Type", dataIndex: "type", key: "type" },
    { title: "Details", dataIndex: "details", key: "details" },
  ];

  const recentMessages = [
    {
      key: 1,
      sender: "Dr. Smith",
      subject: "Appointment Reminder",
      content: "Don't forget your check-up tomorrow!",
    },
    {
      key: 2,
      sender: "Admin",
      subject: "System Update",
      content: "New features have been added to the dashboard.",
    },
    {
      key: 3,
      sender: "Dr. Johnson",
      subject: "Prescription Update",
      content: "Your medication has been adjusted.",
    },
  ];

  const messageColumns = [
    { title: "Sender", dataIndex: "sender", key: "sender" },
    { title: "Subject", dataIndex: "subject", key: "subject" },
    { title: "Message", dataIndex: "content", key: "content" },
  ];

  const topDoctors = [
    { name: "Dr. Smith", specialization: "Pediatrics", patients: 50 },
    { name: "Dr. Johnson", specialization: "Neonatology", patients: 40 },
    { name: "Dr. Brown", specialization: "General Medicine", patients: 35 },
  ];
  //================
  const months = [
    "Jan",
    "Feb",
    "Mar",
    "Apr",
    "May",
    "Jun",
    "Jul",
    "Aug",
    "Sep",
    "Oct",
    "Nov",
    "Dec",
  ];

  const appointmentData = [50, 80, 45, 70, 90, 100, 110, 120, 95, 85, 75, 60];
  const consultationData = [30, 60, 40, 65, 80, 95, 105, 110, 85, 70, 65, 50];

  const growthCategories = [
    { id: 0, value: 30, label: "Underweight", color: "lightblue" },
    { id: 1, value: 150, label: "Normal", color: "lightgreen" },
    { id: 2, value: 20, label: "Overweight", color: "red" },
  ];
  //=====================

  return (
    <div style={{ padding: 20 }}>
      <Row gutter={16}>
        {stats.map((stat, index) => (
          <Col span={3} key={index}>
            <Card style={{ height: "100%", cursor: "pointer" }}>
              <Statistic
                title={stat.title}
                value={stat.value}
                prefix={stat.icon}
              />
            </Card>
          </Col>
        ))}
      </Row>

      <Card title="Charts" style={{ marginTop: 20 }}>
        <Splitter
          style={{
            height: 300,
            boxShadow: "0 0 10px rgba(0, 0, 0, 0.1)",
          }}
        >
          <Splitter.Panel defaultSize="65%" min="30%" max="70%">
            <BarChart
              xAxis={[
                {
                  scaleType: "band",
                  data: months,
                  tickLabelStyle: { textAnchor: "middle", fontSize: 12 },
                },
              ]}
              series={[
                {
                  data: appointmentData,
                  label: "Appointments",
                  color: "#4CAF50",
                },
                {
                  data: consultationData,
                  label: "Consultations",
                  color: "#FF9800",
                },
              ]}
              width={800}
              height={300}
            />
          </Splitter.Panel>
          <Splitter.Panel>
            <Typography.Title
              level={5}
              style={{
                display: "flex",
                justifyContent: "center",
                marginBottom: 30,
              }}
            >
              Child Growth Chart
            </Typography.Title>
            <PieChart
              series={[{ data: growthCategories }]}
              width={400}
              height={200}
            />
          </Splitter.Panel>
        </Splitter>
      </Card>

      <Card title="Recent Messages" style={{ marginTop: 20 }}>
        <Table
          dataSource={recentMessages}
          columns={messageColumns}
          pagination={false}
        />
      </Card>

      <Card title="Top Doctors" style={{ marginTop: 20 }}>
        <List
          itemLayout="horizontal"
          dataSource={topDoctors}
          renderItem={(doctor) => (
            <List.Item>
              <List.Item.Meta
                avatar={<Avatar icon={<ProfileOutlined />} />}
                title={doctor.name}
                description={`${doctor.specialization} - ${doctor.patients} Patients`}
              />
            </List.Item>
          )}
        />
      </Card>
    </div>
  );
};

export default Overview;
