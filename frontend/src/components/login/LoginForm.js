import { EyeInvisibleOutlined, EyeTwoTone, LockOutlined, UserOutlined } from "@ant-design/icons";
import { Button, Card, Col, Flex, Form, Input, message, Row, Tabs } from "antd";
import Cookies from "js-cookie";
import React, { useState } from 'react';
import { login, register } from "../../api/login";


function LoginForm(props) {

    const [mode, setMode] = useState("login");

    console.log("LAM mode", mode);


    const cardStyle = {
        margin: 150,
        width: 880,
    };

    const imgStyle = {
        display: 'block',
        width: 430,
    };

    const userNameComponent = () => {
        return (
            <Form.Item
                label={"Tên đăng nhập"}
                name={"username"}
                rules={[
                    {
                        required: true,
                        message: 'Vui lòng nhập tên đăng nhập',
                    },
                ]}
            >
                <Input prefix={<UserOutlined />}></Input>
            </Form.Item>
        )
    }

    const passwordComponent = () => {
        return (
            <Form.Item
                label={"Mật khẩu"}
                name={"password"}
                rules={[
                    {
                        required: true,
                        message: 'Vui lòng nhập mật khẩu',
                    },
                ]}
            >
                <Input.Password
                    prefix={<LockOutlined />}
                    iconRender={(visible) => (visible ? <EyeTwoTone /> : <EyeInvisibleOutlined />)}>
                </Input.Password>
            </Form.Item>
        )
    }

    const items = [
        {
            label: `Đăng nhập`,
            key: "login",
            children: <>
                <Row>
                    <Col span={24}>
                        {userNameComponent()}
                    </Col>
                </Row>
                <Row>
                    <Col span={24}>
                        {passwordComponent()}
                    </Col>
                </Row>
                <Row>
                    <Col span={12}>
                        <Button htmlType={"submit"}>Đăng nhập</Button>
                    </Col>
                </Row>
            </>,
        },
        {
            label: `Đăng ký`,
            key: "signin",
            children: <>
                <Row>
                    <Col span={24}>
                        {userNameComponent()}
                    </Col>
                </Row>
                <Row>
                    <Col span={24}>
                        {passwordComponent()}
                    </Col>
                </Row>
                <Row>
                    <Col span={24}>
                        <Form.Item
                            label={"Xác nhận mật khẩu"}
                            name={"confirmPassword"}
                            dependencies={['password']}
                            hasFeedback
                            rules={[
                                {
                                    required: true,
                                    message: 'Vui lòng xác nhận lại mật khẩu',
                                },
                                ({ getFieldValue }) => ({
                                    validator(_, value) {
                                        if (!value || getFieldValue("password") === value) {

                                            return Promise.resolve()
                                        }

                                        return Promise.reject(new Error("Mật khẩu mới không trùng với mật khẩu"))
                                    }
                                })
                            ]}
                        >
                            <Input.Password
                                prefix={<LockOutlined />}
                                iconRender={(visible) => (visible ? <EyeTwoTone /> : <EyeInvisibleOutlined />)}>
                            </Input.Password>
                        </Form.Item>
                    </Col>
                </Row>
                <Row>
                    <Col span={12}>
                        <Button type="primary" htmlType={"submit"}>Đăng ký</Button>
                    </Col>
                </Row>
            </>,
        }
    ]

    const onFinish = (values) => {
        if (mode === "login") {
            values = {
                ...values
            }

            delete values.confirmPassword

            login(values).then(res => {
                if (res?.status === 200) {
                    Cookies.set('token', res?.data?.token, { expires: 1 / 24 });
                    Cookies.set('refreshToken', res?.data?.refresh_token, { expires: 1 / 24 });
                    // window.location.href = '/home';
                    window.location.href = '/tasks/all';
                }
            }).catch((err) => {
                if (err?.response?.status === 401) {
                    alert("Ten dang nhap hoac mat khau khong dung")
                }
            })
        } else {
            values = {
                ...values,
                roles: ["EMPLOYEE"]
            }

            delete values.confirmPassword

            console.log("LAM ", values);

            register(values).then(res => {
                message.success("Đăng ký thành công")
                setMode("login")
            }).catch(err => {
                message.error(err.response?.data?.message)
            })
        }
    }


    return (
        <div>

            <Flex justify="center" align="center">
                <Card
                    hoverable
                    style={cardStyle}
                    styles={{
                        body: {
                            padding: 0,
                            overflow: 'hidden',
                        },
                    }}
                >
                    <Flex justify="flex-start">
                        <img
                            alt="avatar"
                            src="https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png"
                            style={imgStyle}
                        />
                        <Flex
                            vertical
                            align="flex-end"
                            justify="flex-start"
                            style={{
                                padding: 32,
                            }}
                        >
                            <Form
                                onFinish={onFinish}
                                layout="vertical"
                                style={{
                                    width: "400px"
                                }}
                            >
                                <Tabs
                                    // defaultActiveKey="login"
                                    activeKey={mode}
                                    centered
                                    items={items}
                                    onChange={setMode}
                                    destroyInactiveTabPane={true}
                                />
                            </Form>
                        </Flex>
                    </Flex>
                </Card>
            </Flex>

        </div>
    );
}

export default LoginForm;