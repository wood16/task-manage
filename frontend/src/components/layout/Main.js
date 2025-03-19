import { Layout, theme } from "antd";
import React from 'react';
import ContentApp from "./ContentApp";
import HeaderApp from "./HeaderApp";
import MenuApp from "./MenuApp";

function Main(props) {

    const {
        token: { colorBgContainer },
    } = theme.useToken();


    return (
        <Layout
            style={{
                minHeight: '100vh',
            }}
        >
            <HeaderApp colorBgContainer={colorBgContainer} />
            <Layout>
                <MenuApp colorBgContainer={colorBgContainer} />
                <ContentApp />
            </Layout>
        </Layout>
    );
}

export default Main;