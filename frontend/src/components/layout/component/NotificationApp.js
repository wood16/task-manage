import { BellOutlined } from '@ant-design/icons';
import { Badge, Card, Dropdown, List } from 'antd';
import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { getNotificationOfUser, patchNotification } from '../../../api/notification';
import { onChangeViewTask } from '../../../redux/actions/layout';

const NotificationApp = (props) => {

    const [data, setData] = useState([])
    const [count, setCount] = useState(0)
    const [loadData, setLoadData] = useState(false)
    const [isOpen, setIsOpen] = useState(false);
    const dispatch = useDispatch()


    useEffect(() => {

        getNotificationOfUser().then(res => {

            console.log("LAM res", res);

            const data = res?.data?.result

            setData(mapData(data))
            setCount(data?.filter(item => item?.status === "noRead")?.length)

        }).catch(

        )

    }, [loadData])

    const handleOnClickItem = (item) => {

        console.log("LAM item", item);

        if (item?.status === "noRead") {
            readNotification(item?.id).then(res => {
                setLoadData(!loadData)
            })
        }

        // view task
        dispatch(onChangeViewTask({
            key: item?.objectId,
            view: true
        }))

        setIsOpen(false);
    }

    const mapData = (data) => {

        return data?.map(item => {

            return {
                ...item,
                label: <div onClick={() => handleOnClickItem(item)}>
                    {
                        item?.status === "noRead" ?
                            <Badge key={'red'} color={'red'} text={<b>{item?.content}</b>} />
                            :
                            <>
                                {item?.content}
                            </>
                    }
                </div>,
                key: item?.id
            }
        })
    }

    const readNotification = async (id) => {

        const object = {
            status: "read"
        }

        return await patchNotification(id, object).then().catch()
    }

    const handleOpenChange = (open) => {
        console.log('Dropdown open state:', open);
        setIsOpen(open);
    }


    return (
        <div>
            <Dropdown
                dropdownRender={(menu) => (
                    <Card>
                        <List
                            header={<b>Thông báo của bạn</b>}
                            footer={<a>Đánh dấu đã đọc</a>}
                            bordered
                            dataSource={data}
                            renderItem={(item) => (
                                <List.Item>
                                    {item?.label}
                                </List.Item>
                            )}
                        />
                    </Card>
                )}
                trigger={['click']}
                open={isOpen}
                placement="bottomRight"
                onOpenChange={handleOpenChange}
            >
                <Badge count={count}>
                    <BellOutlined style={{ fontSize: '20px' }} />
                </Badge>
            </Dropdown>
        </div>
    );
};

export default NotificationApp;