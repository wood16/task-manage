import { HomeOutlined, ProjectOutlined } from "@ant-design/icons";
import { Menu } from "antd";
import Sider from "antd/es/layout/Sider";
import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";

function MenuApp(props) {

    const navigate = useNavigate()
    const pathName = window.location.pathname
    const paths = pathName.split("/").filter(Boolean)
    const [openKey, setOpenKey] = useState([paths[0]])
    const [selectedKey, setSelectedKey] = useState([pathName])

    function getItem(label, key, icon, children) {
        return {
            key,
            icon,
            children,
            label,
        };
    }

    const items = [
        getItem('Công việc', 'tasks', <HomeOutlined />, [
            getItem('Tất cả', '/tasks/all'),
            getItem("Của tôi", '/tasks/myTask')
        ]),
        getItem('Board', 'board', <ProjectOutlined />, [
            getItem('Tất cả', '/board/all'),
            getItem("Của tôi", '/board/myTask')
        ]),
    ];

    const handleOnClickMenu = (value) => {
        setSelectedKey(value?.key)
        navigate(value?.key)
    }

    return (
        <Sider
            style={{
                background: props?.colorBgContainer,
            }}
        >
            <div className="demo-logo-vertical" />
            <Menu
                onClick={(e) => {
                    console.log("LAM e ", e);
                    handleOnClickMenu(e)
                }}
                defaultSelectedKeys={['tasks']}
                mode="inline"
                items={items}
                style={{
                    background: props?.colorBgContainer,
                }}
                openKeys={openKey}
                selectedKeys={selectedKey}
                onOpenChange={(e) => {
                    setOpenKey(e)
                }}
            />
        </Sider>
    );
}

export default MenuApp;