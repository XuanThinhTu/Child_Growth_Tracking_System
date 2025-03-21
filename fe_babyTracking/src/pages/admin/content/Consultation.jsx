import React, { useEffect, useState } from "react";
import { Table, Button, Modal, Select, Tag } from "antd";
import {
  assignConsultation,
  getAllConsultations,
  getAllDoctors,
} from "../../../services/APIServices";
import toast from "react-hot-toast";

const { Option } = Select;

const ConsultationRequests = () => {
  const [doctors, setDoctors] = useState([]);
  const [initialData, setInitialData] = useState([]);
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [selectedDoctor, setSelectedDoctor] = useState(null);
  const [isModalVisible, setIsModalVisible] = useState(false);

  useEffect(() => {
    const fetchDoctors = async () => {
      try {
        const result = await getAllDoctors();
        setDoctors(result);
      } catch (error) {
        console.log(error);
      }
    };
    fetchDoctors();
  }, []);

  useEffect(() => {
    const fetchAllConsultations = async () => {
      try {
        const result = await getAllConsultations();
        console.log(result);
        setInitialData(result);
      } catch (error) {
        console.log(error);
      }
    };
    fetchAllConsultations();
  }, []);

  const handleAssignClick = (request) => {
    setSelectedRequest(request);
    setIsModalVisible(true);
  };

  const handleAssignDoctor = async () => {
    try {
      const result = await assignConsultation(selectedDoctor, selectedRequest);
      setIsModalVisible(false);
      toast.success("Assigned success!");
    } catch (error) {
      console.log(error);
    }
  };

  const handleCancelRequest = (id) => {
    setData((prevData) =>
      prevData.map((item) =>
        item.id === id ? { ...item, status: "cancelled" } : item
      )
    );
  };

  const columns = [
    { title: "Id", dataIndex: "id", key: "id" },
    {
      title: "Baby name",
      dataIndex: "child",
      key: "childName",
      render: (child) => child?.name,
    },
    { title: "Title", dataIndex: "requestTitle", key: "requestTitle" },
    { title: "Note", dataIndex: "note", key: "note" },
    { title: "Create date", dataIndex: "requestDate", key: "requestDate" },
    {
      title: "Status",
      dataIndex: "status",
      key: "status",
      render: (status) => {
        const color =
          status === "PENDING"
            ? "orange"
            : status === "ASSIGNED"
            ? "blue"
            : "red";
        return <Tag color={color}>{status}</Tag>;
      },
    },
    {
      title: "Doctor",
      dataIndex: "doctorName",
      key: "doctorName",
      render: (doctor) => doctor || "-",
    },
    {
      title: "Action",
      key: "action",
      render: (_, record) => {
        if (record.status === "PENDING") {
          return (
            <Button type="primary" onClick={() => handleAssignClick(record.id)}>
              Assign
            </Button>
          );
        }
        if (record.status === "ASSIGNED") {
          return (
            <Button danger onClick={() => handleCancelRequest(record.id)}>
              Cancel
            </Button>
          );
        }
        return null;
      },
    },
  ];

  return (
    <div style={{ padding: 20 }}>
      <h1 style={{ fontSize: "24px", fontWeight: "bold" }}>
        Consultation Requests
      </h1>
      <Table columns={columns} dataSource={initialData} rowKey="id" />

      <Modal
        title="Assign Doctor"
        open={isModalVisible}
        onOk={handleAssignDoctor}
        onCancel={() => setIsModalVisible(false)}
      >
        <p style={{ marginBottom: "10px", fontWeight: 500 }}>
          {" "}
          Request Id: {selectedRequest}
        </p>
        <Select
          style={{ width: "100%" }}
          placeholder="Select a doctor"
          onChange={(value) => setSelectedDoctor(value)}
        >
          {doctors.map((doctor) => (
            <Option key={doctor.id} value={doctor.id}>
              {doctor.firstName}-{doctor.lastName}
            </Option>
          ))}
        </Select>
      </Modal>
    </div>
  );
};

export default ConsultationRequests;
