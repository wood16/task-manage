import { EditOutlined } from '@ant-design/icons';
import { Descriptions, Flex } from 'antd';
import React, { useContext, useState } from 'react';
import InputName from '../common/InputName';
import SelectPriority from '../common/SelectPriority';
import SelectStatus from '../common/SelectStatus';
import { mapDate, mapPrioritySelect, mapStatus } from '../util/map';
import ParentInformation from './ParentInformation';
import { ViewTaskContext } from './ViewTask';

function DetailInformation({ handleOnChangeFieldValue }) {

    const { detailData, selectField, setSelectField } = useContext(ViewTaskContext);
    const [openModal, setOpenModal] = useState(false);
    const formatDate = "DD/MM/YYYY"

    return (
        <>
            <Descriptions
                title={
                    selectField?.type === "name" ?
                        <InputName
                            initValue={detailData?.name}
                            onChange={(value) =>
                                handleOnChangeFieldValue({ name: value })
                            }
                        />
                        :
                        <Flex justify={"flex-start"} align={"center"} gap={"small"}>
                            {detailData?.name}
                            <EditOutlined
                                onClick={() => setSelectField({
                                    type: "name"
                                })}
                            />
                        </Flex>
                }
                column={2}
            >
                <Descriptions.Item label="Trạng thái">
                    {
                        selectField?.type === "status" ?
                            <SelectStatus
                                initValue={detailData?.status}
                                onChange={(value) =>
                                    handleOnChangeFieldValue({ status: value })}
                            /> :
                            <Flex justify={"flex-start"} align={"center"} gap={"small"}>
                                <span>{mapStatus(detailData?.status)}</span>
                                <EditOutlined
                                    onClick={() => setSelectField({
                                        type: "status"
                                    })}
                                />
                            </Flex>
                    }
                </Descriptions.Item>
                {/* <Descriptions.Item label="Tiến độ">
                    {
                        selectField?.type === "progress" ?
                            <InputProgress
                                initValue={detailData?.progress}
                                onChange={(value) =>
                                    handleOnChangeFieldValue({ progress: value })}
                            /> :
                            <Flex justify={"flex-start"} align={"center"} gap={"small"}>
                                <span>{mapProgress(detailData?.progress)}</span>
                                <EditOutlined
                                    onClick={() => setSelectField({
                                        type: "progress"
                                    })}
                                />
                            </Flex>
                    }
                </Descriptions.Item> */}
                <Descriptions.Item label="Độ ưu tiên">
                    {
                        selectField?.type === "priority" ?
                            <SelectPriority
                                value={detailData?.priority}
                                onChange={(value) =>
                                    handleOnChangeFieldValue({ priority: value })}
                            /> :
                            <Flex justify={"flex-start"} align={"center"} gap={"small"}>
                                <span>{mapPrioritySelect(detailData?.priority)}</span>
                                <EditOutlined
                                    onClick={() => setSelectField({
                                        type: "priority"
                                    })}
                                />
                            </Flex>
                    }
                </Descriptions.Item>
                <Descriptions.Item label="Ngày bắt đầu">
                    {mapDate(detailData?.startDate, formatDate)}
                </Descriptions.Item>
                <Descriptions.Item label="Ngày kết thúc">
                    {mapDate(detailData?.endDate, formatDate)}
                </Descriptions.Item>

                <Descriptions.Item label="Người thực hiện">
                    <Flex justify={"flex-start"} align={"center"} gap={"small"}>
                        {detailData?.assigneeName}
                    </Flex>
                </Descriptions.Item>
                <Descriptions.Item label="Người tạo">
                    <Flex justify={"flex-start"} align={"center"} gap={"small"}>
                        {detailData?.creatorName}
                    </Flex>
                </Descriptions.Item>
                <Descriptions.Item label="Công việc cha">
                    <a onClick={() => setOpenModal(true)}>
                        {detailData?.parentTask?.name}
                    </a>
                </Descriptions.Item>
                <Descriptions.Item label="Mô tả">
                    {detailData?.description}
                </Descriptions.Item>

            </Descriptions>
            <ParentInformation
                parentTask={detailData?.parentTask}
                formatDate={formatDate}
                open={openModal}
                handleOnCancel={() => {
                    setOpenModal(false)
                }}
            />
        </>
    );
}

export default DetailInformation;