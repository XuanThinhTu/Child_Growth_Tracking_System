import { DeleteOutlined, EditOutlined } from "@ant-design/icons";
import { Button, Form, Input, Modal, Select, Table, Tooltip } from "antd";
import { useForm } from "antd/es/form/Form";
import { Option } from "antd/es/mentions";
import { useEffect, useState } from "react";
import { getAllUserAccounts } from "../../../services/APIServices";

function DoctorManagement() {
  const [showModal, setShowModal] = useState(false);
  const [formVar] = useForm();
  const [dataSource, setDataSource] = useState([]);

  const handleOpenModal = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleOk = () => {
    formVar.submit();
    handleCloseModal();
  };

  const handlePostAccount = async (values) => {};
  const handleDeleteAccount = async () => {};
  const handleUpdateAccount = async (values) => {};

  //=======================

  useEffect(() => {
    const fetchDoctorRoleAccounts = async () => {
      try {
        const result = await getAllUserAccounts();
        const doctorRoleAccounts = result.data.filter(
          (acc) => acc.role === "ROLE_DOCTOR"
        );

        const formattedData = doctorRoleAccounts.map((user, index) => ({
          key: user.id,
          id: user.id,
          username: `${user.firstName} ${user.lastName}`,
          email: user.email,
          phone: user.phone,
          password: "******",
          role: user.role,
          status: user.active ? "active" : "inactive",
        }));

        setDataSource(formattedData);
      } catch (error) {
        console.log(error);
      }
    };
    fetchDoctorRoleAccounts();
  }, []);

  const columns = [
    {
      title: "No.",
      key: "index",
      render: (_, __, index) => index + 1,
    },
    {
      title: "ID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "User name",
      dataIndex: "username",
      key: "username",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "Phone number",
      dataIndex: "phone",
      key: "phone",
    },
    {
      title: "Role",
      dataIndex: "role",
      key: "role",
    },
    {
      title: "Status",
      dataIndex: "status",
      key: "status",
      render: (status) => (status === "active" ? "🟢 Active" : "🔴 Inactive"),
    },
    {
      title: "Actions",
      key: "actions",
      render: (_, record) => (
        <span>
          <Tooltip title="Edit">
            <EditOutlined
              style={{ color: "blue", marginRight: 10, cursor: "pointer" }}
              onClick={handleUpdateAccount}
            />
          </Tooltip>
          <Tooltip title="Delete">
            <DeleteOutlined
              style={{ color: "red", cursor: "pointer" }}
              onClick={handleDeleteAccount}
            />
          </Tooltip>
        </span>
      ),
    },
  ];
  //========================

  return (
    <div>
      <Button type="primary" onClick={handleOpenModal}>
        Add new doctor
      </Button>
      <Modal
        title="Add new Account"
        open={showModal}
        onCancel={handleCloseModal}
        onOk={handleOk}
      >
        <Form
          labelCol={{ span: 8 }}
          form={formVar}
          onFinish={handlePostAccount}
          labelAlign="left"
        >
          <Form.Item
            label="Username"
            name="username"
            rules={[
              {
                required: true,
                message: "Please input your username!",
              },
            ]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            label="Email"
            name="email"
            rules={[
              {
                required: true,
                message: "Please input your email!",
              },
            ]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            label="Phone Number"
            name="phone"
            rules={[
              {
                required: true,
                message: "Please input your phone number!",
              },
            ]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            label="Password"
            name="password"
            rules={[
              {
                required: true,
                message: "Please input your password!",
              },
            ]}
          >
            <Input.Password />
          </Form.Item>

          <Form.Item
            label="Role"
            name="role"
            rules={[
              {
                required: true,
                message: "Please select role!",
              },
            ]}
          >
            <Select placeholder="Select Role">
              <Option value="User">User</Option>
              <Option value="Doctor">Doctor</Option>
            </Select>
          </Form.Item>
        </Form>
      </Modal>
      <Table dataSource={dataSource} columns={columns} />
    </div>
  );
}

export default DoctorManagement;
