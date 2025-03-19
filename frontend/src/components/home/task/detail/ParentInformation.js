import { Button, Descriptions, Flex, Modal } from 'antd';
import React, { useEffect, useState } from 'react';
import { getTask } from '../../../../api/task';
import { mapDate, mapPrioritySelect, mapStatus } from '../util/map';

function ParentInformation({ parentTask, formatDate, open, handleOnCancel }) {

    const [data, setData] = useState(null)

    useEffect(() => {
        if (open) {
            getTask(parentTask?.id).then(res => {
                setData(res?.data?.result)
            })
        }
    }, [parentTask, open])

    return (
        <div>
            <Modal
                open={open}
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
                <Descriptions
                    title={data?.name}
                    column={2}
                >
                    <Descriptions.Item label="Trạng thái">
                        <span>{mapStatus(data?.status)}</span>
                    </Descriptions.Item>
                    <Descriptions.Item label="Độ ưu tiên">
                        <span>{mapPrioritySelect(data?.priority)}</span>
                    </Descriptions.Item>
                    <Descriptions.Item label="Ngày bắt đầu">
                        {mapDate(data?.startDate, formatDate)}
                    </Descriptions.Item>
                    <Descriptions.Item label="Ngày kết thúc">
                        {mapDate(data?.endDate, formatDate)}
                    </Descriptions.Item>
                    <Descriptions.Item label="Người thực hiện">
                        <Flex justify={"flex-start"} align={"center"} gap={"small"}>
                            {data?.assigneeName}
                        </Flex>
                    </Descriptions.Item>
                    <Descriptions.Item label="Người tạo">
                        <Flex justify={"flex-start"} align={"center"} gap={"small"}>
                            {data?.creatorName}
                        </Flex>
                    </Descriptions.Item>
                    <Descriptions.Item label="Mô tả">
                        {data?.description}
                    </Descriptions.Item>
                </Descriptions>
            </Modal>
        </div>
    );
}

export default ParentInformation;