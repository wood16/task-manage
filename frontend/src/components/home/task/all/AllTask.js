import { DeleteOutlined, EditOutlined } from '@ant-design/icons';
import { Flex, message, Modal, Pagination, Spin, Table } from "antd";
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from "react-redux";
import { deleteTask, getAllTask } from "../../../../api/task";
import { onChangeAddTask, onChangeViewTask, reloadData } from '../../../../redux/actions/layout';
import { columnsList } from '../constant/columns';
import TaskForm from '../form/TaskForm';
import { mapDate, mapPrioritySelect, mapStatus } from '../util/map';

export const AllTaskContext = React.createContext({});

const { confirm } = Modal;

function AllTask({ type, ...props }) {

    const [data, setData] = useState([])
    const [totalCount, setTotalCount] = useState(0)
    const { loading, search, openTask } = useSelector((state) => state.layoutReducer);
    const [selectValue, setSelectValue] = useState(undefined)
    const [loadingData, setLoadingData] = useState(false);
    const [page, setPage] = useState(1);
    const [pageSize, setPageSize] = useState(10);
    const [sorter, setSorter] = useState({});
    const dispatch = useDispatch();

    const formatDate = "DD/MM/YYYY"

    function mapData(data) {
        return data.map(item => {
            return {
                key: item?.id,
                name: <a onClick={() => {
                    dispatch(onChangeViewTask({
                        key: item?.id,
                        view: true
                    }))
                }}>
                    {item?.name}
                </a>,
                progress: item?.progress,
                status: mapStatus(item?.status),
                startDate: mapDate(item?.startDate, formatDate),
                endDate: item?.endDate ? mapDate(item?.endDate, formatDate) : "...",
                creator: item?.creatorName,
                menu: mapMenu(item),
                assigneeName: item?.assigneeName,
                priority: mapPrioritySelect(item?.priority)
            }
        })
    }

    const handleDeleteTask = (value) => {
        confirm({
            title: "Xác nhận",
            content: <>Bạn có chắc chắn muốn xóa công việc <b>{value?.name}</b></>,
            okText: "Xóa",
            cancelText: "Hủy",
            onOk() {
                deleteTask(value?.id).then(() => {
                    message.success("Xóa thành công")
                }).catch(() => {
                    message.error("Xóa thất bại")
                }).finally(() => {
                    dispatch(reloadData());
                })
            },
            onCancel() {

            }
        })

    }

    useEffect(() => {
        setLoadingData(true)

        getAllTask(type, page, pageSize, search, sorter?.sortBy, sorter?.sortOrder).then(res => {
            console.log("LAM res", res?.data?.result);
            setData(mapData(res?.data?.result?.tasks))
            setTotalCount(res?.data.result?.totalItems)
        }).catch(err => {

        }).finally(
            setLoadingData(false)
        )
    }, [loading, search, page, pageSize, sorter, type])

    function mapMenu(item) {
        return <Flex justify={'space-around'} align={'center'}>

            <EditOutlined onClick={() => {
                dispatch(onChangeAddTask({
                    key: item?.id
                }))
                setSelectValue(item)
            }} />

            <DeleteOutlined onClick={() => {
                handleDeleteTask(item)
            }} />
        </Flex>
    }

    const onChangeTable = (pagination, filters, sorter, extra) => {
        setSorter({
            sortBy: sorter?.order ? sorter?.columnKey : null,
            sortOrder: sorter?.order ? (sorter?.order === "ascend" ? "ASC" : "DESC") : null
        })
    }

    const initValueContext = {

    }

    const renderComponent = () => {

        if (loadingData) {

            return (
                <Spin spinning={loadingData}>
                    <div style={{
                        height: "90vh",
                        width: "60vh",
                        backgroundColor: "white"
                    }}></div>
                </Spin>
            )
        } else {

            return (
                <AllTaskContext.Provider value={initValueContext}>
                    <h1>Danh sách công việc</h1>

                    <Table
                        columns={columnsList}
                        dataSource={data}
                        pagination={false}
                        onChange={onChangeTable}
                    />
                    <Pagination
                        style={{
                            padding: "5px 0px"
                        }}
                        align='end'
                        total={totalCount}
                        defaultCurrent={page}
                        defaultPageSize={pageSize}
                        onChange={(page, pageSize) => {
                            setPage(page)
                            setPageSize(pageSize)
                        }}
                        showSizeChanger
                        showTotal={(total) => `Tổng ${total} công việc`}
                    />
                    <TaskForm
                        open={openTask}
                        handleClose={() => {
                            dispatch(onChangeAddTask({
                                key: undefined
                            }))
                            setSelectValue(undefined)
                        }}
                        id={selectValue?.id}
                    />

                </AllTaskContext.Provider>
            )
        }
    }

    return (
        <>
            {
                renderComponent()
            }
        </>
    );
}

export default AllTask;