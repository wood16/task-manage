import { Content } from "antd/es/layout/layout";
import React from 'react';
import { useSelector } from 'react-redux';
import ViewTask from '../home/task/detail/ViewTask';
import MainRouter from "./router";

function ContentApp(props) {

    const { viewTask } = useSelector((state) => state.layoutReducer);

    return (
        <Content
            style={{
                margin: '0 16px',
            }}
        >
            <MainRouter />
            {
                viewTask?.view &&
                <ViewTask
                    open={viewTask}
                />
            }
        </Content>
    );
}

export default ContentApp;