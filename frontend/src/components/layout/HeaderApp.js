import { LogoutOutlined, UserOutlined } from "@ant-design/icons";
import { Avatar, Button, Col, Flex, Row } from "antd";
import Search from 'antd/es/input/Search';
import { Header } from "antd/es/layout/layout";
import Cookies from "js-cookie";
import { jwtDecode } from "jwt-decode";
import React from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from "react-router-dom";
import { getAccessToken } from "../../custom/axios";
import { onChangeAddTask, onChangeSearchData } from '../../redux/actions/layout';
import NotificationApp from "./component/NotificationApp";

function HeaderApp(props) {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const accessToken = getAccessToken();
    const userInfo = accessToken ? jwtDecode(accessToken) : undefined

    function handleLogout() {
        Cookies.set('token', "");
        navigate("/login")
    }

    const handleOnSearch = (value) => {
        dispatch(onChangeSearchData(value))
    }

    return (
        <Header
            style={{
                padding: 0,
                background: props?.colorBgContainer,
            }}
        >
            <Row>
                <Col span={"7"}>

                </Col>
                <Col span={"10"}>
                    <div
                        style={{
                            paddingTop: "16px"
                        }}
                    >
                        <Flex align={"center"} gap={"small"} justify="center">
                            <Search
                                placeholder="Tìm kiếm công việc"
                                onSearch={handleOnSearch}
                                enterButton
                                onPressEnter={(e) => handleOnSearch(e.target.value)}
                            />
                            <Button type="primary" onClick={() => {
                                dispatch(onChangeAddTask({
                                    key: "add"
                                }))
                            }}>
                                Tạo công việc
                            </Button>
                        </Flex>
                    </div>
                </Col>
                <Col span={"7"}>
                    <Flex align={"center"} justify={"flex-end"} gap={"small"}>
                        <NotificationApp />
                        <Flex align={"center"} justify={"flex-end"} gap={"small"}>
                            <Avatar
                                style={{
                                    backgroundColor: '#87d068',
                                }}
                                icon={<UserOutlined />}
                            />
                            {userInfo?.sub}
                        </Flex>
                        <Button onClick={() => handleLogout()}>Đăng xuất <LogoutOutlined /></Button>
                    </Flex>
                </Col>
            </Row>

        </Header>
    );
}

export default HeaderApp;