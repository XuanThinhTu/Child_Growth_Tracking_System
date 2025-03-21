import {
  Badge,
  Button,
  Calendar,
  Form,
  Input,
  Modal,
  Popconfirm,
  TimePicker,
} from "antd";
import { useForm } from "antd/es/form/Form";
import dayjs from "dayjs";
import { useState, useEffect } from "react";

function AppointmentManagement() {
  const [showModal, setShowModal] = useState(false);
  const [currentMonth, setCurrentMonth] = useState(dayjs());
  const [selectedDate, setSelectedDate] = useState("");
  const [editing, setEditing] = useState(false);
  const [formVar] = useForm();

  const handleOpenModal = () => {
    setShowModal(true);
  };

  const handleCLoseModal = () => {
    setEditing(false);
    setShowModal(false);
  };

  const handleOk = () => {
    formVar.submit();
    handleCLoseModal();
  };

  const onDateChange = (value) => {
    handleOpenModal();
    setSelectedDate(dayjs(value).format("DD/MM/YYYY"));
  };

  const handlePostAppointment = async (values) => {};

  const handleUpdateAppointment = async (values) => {
    setEditing(!editing);
  };

  const handleDeleteAppointment = async () => {};

  const appointmentData = [
    {
      date: "2025-02-05",
      event: "Doctor Jones's Appointment",
    },
    {
      date: "2025-02-14", //(YYYY-MM-DD)
      event: "Valentine's Day",
    },
    {
      date: "2025-02-20",
      event: "Doctor Emily's Appointment",
    },
    {
      date: "2025-02-24",
      event: "Doctor Clark's Appointment",
    },
  ];

  const eventDate = (value) => {
    const dateString = value.format("YYYY-MM-DD");
    const event = appointmentData.find((e) => e.date === dateString);

    return event ? <Badge color="blue" text={event.event} /> : null;
  };

  return (
    <div>
      <Calendar
        onSelect={(value) => {
          if (
            value.month() === currentMonth.month() &&
            value.year() === currentMonth.year()
          ) {
            onDateChange(value);
          }
        }}
        onPanelChange={(value) => {
          setCurrentMonth(value);
        }}
        dateCellRender={eventDate}
      />
      <Modal
        title="Appointment Information"
        open={showModal}
        onOk={handleOk}
        onCancel={handleCLoseModal}
      >
        <p>Meeting Appointment ID: 1234567</p>
        <p>Date: {selectedDate}</p>
        <Form
          labelCol={{ span: 8 }}
          labelAlign="left"
          form={formVar}
          onFinish={editing ? handleUpdateAppointment : handlePostAppointment}
          initialValues={{
            doctor_id: "dr_jones",
            children_id: "Tyler",
            content: "Fever and Headache",
            start_time: dayjs("09:00", "HH:mm"),
            end_time: dayjs("09:45", "HH:mm"),
            meeting_link:
              "To join the video meeting, click this link: https://meet.google.com/qun-esvf-rpf\nOtherwise, to join by phone, dial +1 904-323-3298 and enter this PIN: 178 806 490#",
          }}
        >
          <Form.Item label="Doctor" name="doctor_id">
            <Input disabled={!editing} />
          </Form.Item>
          <Form.Item label="Children" name="children_id">
            <Input disabled={!editing} />
          </Form.Item>
          <Form.Item label="Content" name="content">
            <Input.TextArea disabled={!editing} />
          </Form.Item>
          <Form.Item label="Start from" name="start_time">
            <TimePicker disabled={!editing} />
          </Form.Item>
          <Form.Item label="End at" name="end_time">
            <TimePicker disabled={!editing} />
          </Form.Item>
          <Form.Item label="Meeting Link" name="meeting_link">
            <Input.TextArea disabled={!editing} />
          </Form.Item>
        </Form>
        <div
          style={{ display: "flex", justifyContent: "flex-end", gap: "10px" }}
        >
          <Button onClick={handleUpdateAppointment} type="primary">
            {editing ? "Save Change" : "Edit Information"}
          </Button>
          <Popconfirm
            title="Delete this appointment"
            description="DO you want to delete this appointment?"
            okText="Delete"
            cancelText="Cancel"
            onConfirm={handleDeleteAppointment}
            onCancel={handleCLoseModal}
          >
            <Button
              onClick={handleDeleteAppointment}
              style={{ backgroundColor: "red", color: "white" }}
            >
              Delete Appointment
            </Button>
          </Popconfirm>
        </div>
      </Modal>
    </div>
  );
}

export default AppointmentManagement;
