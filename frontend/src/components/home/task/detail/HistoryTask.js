import { AuditOutlined, CalendarOutlined, UserOutlined } from '@ant-design/icons';
import { Empty, Timeline } from 'antd';
import React, { useEffect, useState } from 'react';
import { getHistoryObject } from '../../../../api/history';
import { mapDate } from '../util/map';
import { useSelector } from 'react-redux';

const HistoryTask = ({ taskId }) => {

    const formatDate = "HH:mm DD/MM/YYYY"
    const [dataHistory, setDataHistory] = useState([])
    const { loading } = useSelector(state => state.layoutReducer)

    useEffect(() => {

        // khong load het data

        getHistoryObject("task", taskId).then(res => {
            setDataHistory(res?.data?.result)
        })
    }, [taskId, loading])

    const mapFieldValue = (field, value) => {

        switch (field) {
            case "priority": {
                switch (value) {
                    case "high":
                        return "Cao"
                    case "normal":
                        return "Trung bình"
                    case "low":
                        return "Thấp"
                    default:
                        return ""
                }
            }
            case "status": {
                switch (value) {
                    case "pending":
                        return "Chờ xử lý"
                    case "processing":
                        return "Đang xử lý"
                    case "complete":
                        return "Hoàn thành"
                    case "cancel":
                        return "Hủy"
                    case "pause":
                        return "Tạm dừng"
                    default:
                        return ""
                }
            }
            default:
                return value;
        }
    }

    const mapDetail = (item) => {
        if (item?.action === "update" && item?.field !== "") {
            return (
                <>
                    <AuditOutlined /> Từ "<span>{mapFieldValue(item?.field, item?.fromValue)}</span>
                    " sang "<span>{mapFieldValue(item?.field, item?.toValue)}</span>"
                </>
            )
        }
    }

    const mapHistories = () => {

        return dataHistory?.map(item => {
            return (
                <Timeline.Item>
                    <div>{item?.description}</div>
                    <div>{mapDetail(item)}</div>
                    <div><UserOutlined /> {item?.creatorName}</div>
                    <div><CalendarOutlined /> {mapDate(item?.createDate, formatDate)}</div>
                </Timeline.Item>
            )
        })
    }

    const renderComponent = () => {

        if (dataHistory && dataHistory?.length > 0) {

            return (
                <Timeline mode='left'>
                    {mapHistories()}
                </Timeline>
            )
        }

        return <Empty description="Chưa có dữ liệu" />
    }

    return (
        <div style={{
            height: "400px",
            overflow: "hidden scroll",
            paddingTop: "2px"
        }}>
            {renderComponent()}

        </div>

    );
};

export default HistoryTask;