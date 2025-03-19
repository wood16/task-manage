import { Button, Col, DatePicker, Flex, Form, Input, Modal, Row, message } from "antd";
import { useForm } from "antd/es/form/Form";
import TextArea from "antd/es/input/TextArea";
import dayjs from "dayjs";
import React, { useEffect } from "react";
import { useDispatch } from "react-redux";
import { getTask, postTask, putTask } from "../../../../api/task";
import { getUserInfo } from "../../../../custom/axios";
import { reloadData } from "../../../../redux/actions/layout";
import SelectAssignee from "../common/SelectAssignee";
import SelectParentTask from "../common/SelectParentTask";
import SelectPriority from "../common/SelectPriority";

const TaskForm = ({ id, open, handleClose }) => {
  const dispatch = useDispatch();
  const [form] = useForm();
  const userInfo = getUserInfo()

  const formatDate = "DD/MM/YYYY"

  const handleOnClose = () => {
    form.resetFields()
    handleClose()
  }

  const handleAssignToMe = () => {
    form.setFieldValue('assigneeId', userInfo?.userId)
  }

  const onFinish = (values) => {

    values = {
      ...values,
    };

    if (id) {
      putTask(id, values).then(() => {
        dispatch(reloadData());
        handleOnClose();
        message.success("Cập nhật công việc thành công")
      }).catch((err) => {
        message.error(err.response?.data?.message);
      });
    }
    else {
      postTask(values).then(res => {
        dispatch(reloadData());
        handleOnClose()
        message.success("Tạo công việc thành công")
      }
      ).catch((err) => {
        message.error(err.response?.data?.message);
      })
    }
  };

  useEffect(() => {

    if (id) {
      getTask(id).then(res => {

        form.setFieldsValue({
          ...res.data,
          startDate: dayjs(res.data?.startDate),
          endDate: dayjs(res.data?.endDate ? res.data?.endDate : new Date()),
        })
      }).catch()
    }

  }, [id])

  return (
    <div>
      <Modal
        open={id ? open?.key === id : open?.key === "add"}
        onCancel={() => handleOnClose()}
        onOk={() => form.submit()}
        width={"50vw"}
        destroyOnClose
        footer={[
          <Button onClick={() => handleOnClose()}>
            Hủy
          </Button>,
          <Button type="primary" onClick={() => form.submit()}>
            {
              id ? "Cập nhật" : "Tạo mới"
            }
          </Button>
        ]}
      >
        <Form layout="vertical" onFinish={onFinish} form={form}>
          <Row>
            <Col span={24}>
              <Form.Item
                label={"Tên công việc"}
                name={"name"}
                rules={[
                  {
                    required: true,
                    message: "Tên công việc là bắt buộc"
                  },
                ]}
              >
                <Input placeholder="Nhập tên công việc" />
              </Form.Item>
            </Col>
          </Row>
          <Row>
            <Col span={11}>
              <Form.Item
                label={"Thời gian bắt đầu"}
                name={"startDate"}
                rules={[
                  {
                    required: true,
                    message: "Thời gian bắt đầu là bắt buộc"
                  },
                ]}
                initialValue={dayjs(new Date())}
              >
                <DatePicker
                  style={{
                    width: "100%",
                  }}
                  format={formatDate}
                  placeholder="Chọn ngày"
                />
              </Form.Item>
            </Col>
            <Col span={2}></Col>
            <Col span={11}>
              <Form.Item
                label={"Thời gian kết thúc"}
                name={"endDate"}
                rules={[
                  {
                    required: true,
                    message: "Thời gian kết thúc là bắt buộc"
                  },
                ]}
                initialValue={dayjs(new Date())}
              >
                <DatePicker
                  style={{
                    width: "100%",
                  }}
                  format={formatDate}
                  placeholder="Chọn ngày"
                />
              </Form.Item>
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <Form.Item
                label={"Người thực hiện"}
                name={"assigneeId"}
                rules={[
                  {
                    required: true,
                    message: "Người thực hiện là bắt buộc"
                  }
                ]}
              >
                <SelectAssignee />
              </Form.Item>
            </Col>
            <Col span={24}>
              <Flex justify={"space-between"} align={"flex-start"}>
                <div></div>
                <a onClick={() => handleAssignToMe()}>Gán cho tôi</a>
              </Flex>
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <Form.Item
                label={"Độ ưu tiên"}
                name={"priority"}
                initialValue={"normal"}
              >
                <SelectPriority />
              </Form.Item>
            </Col>
          </Row>
          {/* <Row>
            <Col span={24}>
              <Form.Item
                label={"Cách tính tiến độ"}
                name={"progressType"}
                initialValue={"updated"}
              >
                <SelectProgressType />
              </Form.Item>
            </Col>
          </Row> */}
          <Row>
            <Col span={24}>
              <Form.Item label={"Công việc cha"} name={"parentId"}>
                <SelectParentTask taskId={id} />
              </Form.Item>
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <Form.Item label={"Mô tả"} name={"description"}>
                <TextArea
                  placeholder="Nhập mô tả"
                  autoSize={{
                    minRows: 3,
                    maxRows: 5,
                  }}
                />
              </Form.Item>
            </Col>
          </Row>
        </Form>
      </Modal>
    </div>
  );
};

export default TaskForm;
