import { ArrowRightOutlined, CalendarOutlined, UserOutlined } from '@ant-design/icons';
import { Empty, Timeline } from 'antd';
import React from 'react';
import { mapDate } from '../util/map';

function ProgressHistoryTask({ data }) {

    const formatDate = "HH:mm DD/MM/YYYY"

    const mapHistories = () => {

        return data?.map(item => {
            return (
                <Timeline.Item>
                    <div>Thay đổi tiến độ: {item?.fromProgress}% <ArrowRightOutlined /> {item?.toProgress}% </div>
                    <div><UserOutlined /> {item?.creatorName}</div>
                    <div><CalendarOutlined /> {mapDate(item?.createDate, formatDate)}</div>
                </Timeline.Item>
            )
        })
    }

    const renderComponent = () => {

        if (data && data?.length > 0) {

            return (
                <Timeline mode='left'>
                    {mapHistories()}
                </Timeline>
            )
        }

        return <Empty description="Chưa có dữ liệu" />
    }

    return (
        <>
            {renderComponent()}
        </>

    );
}

export default ProgressHistoryTask;