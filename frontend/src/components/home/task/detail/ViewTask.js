import { HistoryOutlined, UserAddOutlined, UserOutlined } from '@ant-design/icons';
import { Button, message, Modal, Tabs } from 'antd';
import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { getTask, patchTask } from '../../../../api/task';
import { onChangeViewTask, reloadData } from '../../../../redux/actions/layout';
import DetailInformation from './DetailInformation';
import HistoryTask from './HistoryTask';
import ProgressHistoryTask from './ProgressHistoryTask';

export const ViewTaskContext = React.createContext({})

function ViewTask({ open, onCancel, ...props }) {

    const dispatch = useDispatch()
    const [selectField, setSelectField] = useState(undefined)
    const [detailData, setDetailData] = useState(undefined)

    const mapData = (data) => {
        return {
            ...data,
            assigneeName: <><UserOutlined /> {data?.assigneeName}</>,
            creatorName: <><UserAddOutlined /> {data?.creatorName}</>,
        }
    }

    useEffect(() => {
        getTask(open?.key).then(res => {
            console.log("LAM ", res);
            setDetailData(mapData(res?.data?.result))
        })
    }, [open])


    const handleOnChangeFieldValue = (value) => {

        patchTask(open?.key, value).then(res => {
            message.success("Cập nhật thành công")
            setDetailData(mapData(res?.data?.result))
            dispatch(reloadData());
        }).catch((err) => {
            message.error(err.response?.data?.message);
        })

        setSelectField(undefined)
    }

    const handleOnCancel = () => {
        dispatch(onChangeViewTask({
            key: undefined,
            view: undefined
        }))
        setSelectField(undefined)
    }

    const itemTabs = [
        {
            label: (
                <span>
                    Chi tiết
                </span>
            ),
            key: "detail",
            children: <DetailInformation
                handleOnChangeFieldValue={handleOnChangeFieldValue}
            />
        },
        {
            label: (
                <span>
                    <HistoryOutlined /> Lịch sử báo cáo
                </span>
            ),
            key: "progressHistory",
            children: <ProgressHistoryTask data={detailData?.progressHistories} />
        },
        {
            label: (
                <span>
                    <HistoryOutlined /> Lịch sử
                </span>
            ),
            key: "history",
            children: <HistoryTask taskId={open?.key} />
        }
    ]

    const initValueContext = {
        selectField, setSelectField, detailData, setDetailData
    }

    return (
        <ViewTaskContext.Provider value={initValueContext}>
            <Modal
                open={open?.view}
                onCancel={() => {
                    handleOnCancel()
                }}
                width={"50vw"}
                footer={
                    <div>
                        <Button
                            onClick={() => {
                                handleOnCancel()
                            }}
                        >
                            Quay lại
                        </Button>
                    </div>
                }
                destroyOnClose
            >
                <Tabs items={itemTabs} />
            </Modal>
        </ViewTaskContext.Provider>
    );
}

export default ViewTask;