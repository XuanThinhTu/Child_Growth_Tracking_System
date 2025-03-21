import { useState, useEffect } from "react";
import { Table, Input, Select } from "antd";
import { SearchOutlined } from "@ant-design/icons";
import { getAllUserAccounts } from "../../../services/APIServices";

const { Option } = Select;

const AccountManagement = () => {
  const [dataSource, setDataSource] = useState([]);

  useEffect(() => {
    const fectUserAccounts = async () => {
      try {
        const result = await getAllUserAccounts();

        const formattedData = result.data.map((user, index) => ({
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
    fectUserAccounts();
  }, []);

  console.log(dataSource);

  const [filteredAccounts, setFilteredAccounts] = useState(dataSource);
  const [searchText, setSearchText] = useState("");
  const [selectedRole, setSelectedRole] = useState("All");

  useEffect(() => {
    let filteredData = dataSource;

    if (searchText) {
      filteredData = filteredData.filter(
        (account) =>
          account.username?.toLowerCase().includes(searchText?.toLowerCase()) ||
          account.email?.toLowerCase().includes(searchText?.toLowerCase())
      );
    }

    if (selectedRole && selectedRole !== "All") {
      filteredData = filteredData.filter(
        (account) => account.role === selectedRole
      );
    }

    setFilteredAccounts(filteredData);
  }, [searchText, selectedRole, dataSource]);

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
  ];

  return (
    <div className="p-4 bg-white rounded-lg shadow-md">
      <div className="flex justify-between mb-4">
        <Input
          placeholder="Search by name or email"
          prefix={<SearchOutlined />}
          value={searchText}
          onChange={(e) => setSearchText(e.target.value)}
          className="w-1/3"
        />

        <Select
          placeholder="Filter by role"
          value={selectedRole}
          onChange={(value) => setSelectedRole(value)}
          allowClear
          className="w-1/4"
        >
          <Option value="All">Select All</Option>
          <Option value="ROLE_USER">User</Option>
          <Option value="ROLE_DOCTOR">Doctor</Option>
        </Select>
      </div>

      <Table
        dataSource={filteredAccounts}
        columns={columns}
        rowKey="id"
        pagination={{ pageSize: 10 }}
      />
    </div>
  );
};

export default AccountManagement;
