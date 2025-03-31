import React, { useEffect, useState } from "react";
import { Table, Button, Modal, Select, Tag, List, Typography } from "antd";
import {
  assignConsultation,
  getAllConsultations,
  getAllDoctors,
  getConsultationReplies,
} from "../../../services/APIServices";
import toast from "react-hot-toast";

const { Option } = Select;
const { Text } = Typography;

const ConsultationRequests = () => {
  const [doctors, setDoctors] = useState([]);
  const [initialData, setInitialData] = useState([]);
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [selectedDoctor, setSelectedDoctor] = useState(null);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [isDetailModalVisible, setIsDetailModalVisible] = useState(false);
  const [replies, setReplies] = useState([]);

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
    setIsDetailModalVisible(false);
    setSelectedRequest(request);
    setIsModalVisible(true);
  };

  const handleAssignDoctor = async () => {
    try {
      const result = await assignConsultation(selectedDoctor, selectedRequest);
      setIsModalVisible(false);
      if (result) {
        toast.success("Assigned success!");
      } else {
        toast.error("Assigned failed!");
      }
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

  const handleDetailClick = (record) => {
    setSelectedRequest(record);
    setIsDetailModalVisible(true);
  };

  useEffect(() => {
    const fetchConsultationReplies = async () => {
      try {
        const result = await getConsultationReplies(selectedRequest?.id);
        setReplies(result);
      } catch (error) {
        console.log(error);
      }
    };
    fetchConsultationReplies();
  }, [selectedRequest?.id]);

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
            : status === "PROCESSING"
            ? "green"
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
      render: (_, record) => (
        <div style={{ display: "flex", gap: "8px" }}>
          {record.status === "PENDING" && (
            <Button type="primary" onClick={() => handleAssignClick(record.id)}>
              Assign
            </Button>
          )}
          {(record.status === "PROCESSING" || record.status === "ASSIGNED") && (
            <Button danger onClick={() => handleCancelRequest(record.id)}>
              Cancel
            </Button>
          )}
          <Button onClick={() => handleDetailClick(record)}>View</Button>
        </div>
      ),
    },
  ];

  return (
    <div style={{ padding: 20 }}>
      <h1 style={{ fontSize: "24px", fontWeight: "bold" }}>
        Consultation Requests
      </h1>
      <Table columns={columns} dataSource={initialData} rowKey="id" />

      <Modal
        title="Consultation Details"
        open={isDetailModalVisible}
        onCancel={() => setIsDetailModalVisible(false)}
        footer={null}
      >
        {selectedRequest && (
          <>
            <p>
              <strong>Request Id:</strong> {selectedRequest.id}
            </p>
            <p>
              <strong>Baby Name:</strong> {selectedRequest.child?.name}
            </p>
            <p>
              <strong>Doctor:</strong>{" "}
              {selectedRequest.doctorName || "Not Assigned"}
            </p>
            <p>
              <strong>Request Title:</strong> {selectedRequest.requestTitle}
            </p>
            <p>
              <strong>Note:</strong> {selectedRequest.note}
            </p>
          </>
        )}

        <h3 style={{ marginTop: 20 }}>Chat History</h3>
        <List
          bordered
          dataSource={replies}
          renderItem={(item) => (
            <List.Item>
              <div>
                <Text type="secondary">
                  {new Date(item.createdAt).toLocaleString()}
                </Text>
                <p style={{ margin: 0 }}>{item.content}</p>
              </div>
            </List.Item>
          )}
        />
      </Modal>

      <Modal
        title="Assign Doctor"
        open={isModalVisible}
        onOk={handleAssignDoctor}
        onCancel={() => setIsModalVisible(false)}
      >
        <p style={{ marginBottom: "10px", fontWeight: 500 }}>
          {" "}
          Request Id: {selectedRequest?.id}
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
