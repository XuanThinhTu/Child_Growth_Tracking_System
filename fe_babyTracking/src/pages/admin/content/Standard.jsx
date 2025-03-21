import { UploadOutlined } from "@ant-design/icons";
import { Button, Card, Col, Form, message, Modal, Row, Upload } from "antd";
import { Typography } from "antd";
import { useForm } from "antd/es/form/Form";
const { Title } = Typography;
import React, { useState } from "react";

function Standard() {
  const [selectedCard, setSelectedCard] = useState(null);
  const [isOpen, setIsOpne] = useState(false);
  const [formVar] = useForm();

  const growthStandardData = [
    {
      key: "0-5",
      title: "Growth Standard for 0-5 years old",
      description:
        "During the first five years, children experience rapid physical growth and cognitive development. Proper nutrition, vaccinations, and regular health check-ups are crucial. Key milestones include motor skills development, speech and language progression, and social interactions.",
      file: "/files/growth_0_5.pdf",
    },
    {
      key: "5-19",
      title: "Growth Standard for 5-19 years old",
      description:
        "From ages 5 to 19, growth continues steadily, with puberty bringing significant changes. Physical development varies, and cognitive skills advance, affecting learning and behavior. A balanced diet, physical activity, and mental well-being support healthy development.",
      file: "/files/growth_5_19.pdf",
    },
  ];

  const handleOpenModal = (value) => {
    setIsOpne(true);
    setSelectedCard(value);
  };
  console.log(selectedCard);

  const handleCLoseModal = () => {
    setIsOpne(false);
    setSelectedCard("");
  };

  const handleOk = () => {
    formVar.submit();
    handleCLoseModal();
  };

  const handleUploadFile = (info) => {
    if (info.file.status === "done") {
      message.success(`${info.file.name} file upload successfully!`);
    } else if (info.file.status === "error") {
      message.error(`${info.file.name} file uplaod failed!`);
    }
  };

  return (
    <div>
      <Title level={1} style={{ display: "flex", justifyContent: "center" }}>
        Child Growth Standard
      </Title>
      <Row gutter={[16, 16]} justify="center">
        {growthStandardData.map((item) => (
          <Col span={8} key={item}>
            <Card
              title={item.title}
              variant="borderless"
              style={{
                width: "100%",
                minHeight: "400px",
                fontSize: "18px",
                padding: "20px",
                cursor: "pointer",
              }}
              onClick={() => handleOpenModal(item)}
            >
              {item.description}
            </Card>
          </Col>
        ))}
      </Row>
      <Modal
        title={selectedCard?.title}
        open={isOpen}
        onOk={handleOk}
        onCancel={handleCLoseModal}
      >
        <div style={{ marginBottom: "30px" }}>{selectedCard?.description}</div>
        <Form form={formVar} layout="vertical">
          <Form.Item
            label="Upload Growth Data (Excel file)"
            rules={[{ required: true, message: "Please upload an Excel file" }]}
          >
            <Upload
              name="file"
              action="/upload"
              accept=".xlsx,.xls"
              maxCount={1}
              onChange={handleUploadFile}
            >
              <Button icon={<UploadOutlined />}>Click to Upload</Button>
            </Upload>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
}

export default Standard;
