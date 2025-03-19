import { Flex, message, Modal } from 'antd';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Board from 'react-trello';
import { deleteTask, getAllTask, patchTask } from '../../../../api/task';
import { reloadData } from '../../../../redux/actions/layout';
import { dataStatus, statusTask } from '../../task/constant/status';
import { mapDate, mapPrioritySelect, mapStatusSelect } from '../../task/util/map';

const { confirm } = Modal;

const AllBoard = ({ type }) => {

    const [data, setData] = useState(undefined)
    const { loading, search } = useSelector((state) => state.layoutReducer);
    const dispatch = useDispatch();
    const formatDate = "DD/MM/YYYY"

    console.log("LAM data", data);

    useEffect(() => {
        getAllTask(type, 1, 100, search, null, null).then(res => {
            console.log("LAM res", res);

            const result = {};

            statusTask.map(status => {
                result[status] = res?.data?.result?.tasks?.filter(item => item?.status === status)
            })

            setData(result)

        }).catch(err => {

        }).finally(

        )
    }, [search, type])

    const mapCards = (array) => {

        console.log("LAM array ", array);

        return array?.map(item => {
            return {
                id: item?.id,
                title: item?.name,
                description: <Flex justify='space-between'>
                    <div>{mapDate(item?.startDate, formatDate) + ' - ' + mapDate(item?.endDate, formatDate)}</div>
                    {item?.assigneeName}
                </Flex>,
                label: mapPrioritySelect(item?.priority),
                // draggable: false
            }
        })
    }

    const mapDataSource = (data) => {
        const response = dataStatus?.map(status => {
            return ({
                id: status?.value,
                title: mapStatusSelect(status?.value),
                label: data[status?.value]?.length,
                cards: mapCards(data[status?.value])
            })
        })

        console.log("LAM a", response);

        return {
            lanes: response
        }
    }

    const updateData = (cardId, sourceLaneId, targetLaneId) => {
        const sourceData = data[sourceLaneId]
        const value = sourceData.find(item => item?.id === cardId)
        const newSource = sourceData.filter(item => item?.id !== cardId)

        const targetData = data[targetLaneId]
        const newTarget = [value, ...targetData]

        setData({
            ...data,
            [sourceLaneId]: newSource,
            [targetLaneId]: newTarget
        })
    }

    const handleDragEnd = (cardId, sourceLaneId, targetLaneId, position, cardDetails) => {

        console.log("LAM 123", cardId, sourceLaneId, targetLaneId, position, cardDetails);

        if (sourceLaneId !== targetLaneId) {

            updateData(cardId, sourceLaneId, targetLaneId)
            // reload data when have error

            patchTask(
                cardId,
                {
                    status: targetLaneId
                }
            ).then(res => {
                message.success("Cập nhật thành công")

                dispatch(reloadData());

            }).catch((err) => {
                message.error(err.response?.data?.message);
            })
        }
    }

    const handleDelete = (cardId, laneId) => {

        const sourceData = data[laneId]
        const newSource = sourceData.filter(item => item?.id !== cardId)

        setData({
            ...data,
            [laneId]: newSource,
        })

        deleteTask(cardId).then(() => {
            message.success("Xóa thành công")
        }).catch(() => {
            message.error("Xóa thất bại")
        }).finally(() => {
            dispatch(reloadData());
        })
    }

    const onBeforeCardDelete = (handleDelete) => {

        confirm({
            title: "Xác nhận",
            content: "Bạn có chắc chắn muốn xóa công việc ",
            okText: "Xóa",
            cancelText: "Hủy",
            onOk() {
                handleDelete()
            },
            onCancel() {

            }
        })
    }

    return (
        <div>
            {
                data &&
                <Board
                    laneDraggable={false}
                    data={mapDataSource(data)}
                    style={{ backgroundColor: '#f5f5f5' }}
                    handleDragEnd={handleDragEnd}
                    onBeforeCardDelete={(handleDelete) => {
                        onBeforeCardDelete(handleDelete)
                    }}
                    onCardDelete={(cardId, laneId) => handleDelete(cardId, laneId)}
                />
            }
        </div>
    );
};

export default AllBoard;