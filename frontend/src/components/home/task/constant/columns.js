

export const columnsList = [
    {
        title: 'Tên công việc',
        dataIndex: 'name',
        key: 'name'
    },
    // {
    //     title: 'Tiến độ',
    //     dataIndex: 'progress',
    //     key: 'progress',
    // },
    {
        title: 'Trạng thái',
        dataIndex: 'status',
        key: 'status',
        width: "12%"
    },
    {
        title: 'Ưu tiên',
        dataIndex: 'priority',
        key: 'priorityNumber',
        sorter: true,
        width: "12%"
    },
    {
        title: 'Bắt đầu',
        dataIndex: 'startDate',
        key: 'startDate',
        width: "12%"
    },
    {
        title: 'Kết thúc',
        dataIndex: 'endDate',
        key: 'endDate',
        width: "12%"
    },
    {
        title: 'Người thực hiện',
        dataIndex: 'assigneeName',
        key: 'assigneeName',
        width: "15%"
    },
    {
        title: '',
        dataIndex: 'menu',
        key: 'menu',
        width: '80px'
    },

]