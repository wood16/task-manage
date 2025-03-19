import React, { useEffect, useState } from 'react';
import { getAllTask } from "../../../../api/task";
import { Select } from "antd";

const Option = Select.Option

const SelectParentTask = ({ value, onChange, taskId }) => {

    const [data, setData] = useState([])

    useEffect(() => {
        getAllTask(null, 0, 20, null, null).then(res => {
            setData(res?.data?.result?.tasks?.filter(item => item?.id !== taskId))
        }).catch(err => {

        }).finally(

        )
    }, [])

    const mapSelectData = () => {
        return data?.map((item) => {
            return (
                <Option value={item?.id} key={item?.id}>
                    {item?.name}
                </Option>
            )
        })
    }

    const handleSelectChange = (selectedValue) => {
        onChange(selectedValue);
    };

    return (
        <Select
            style={{
                width: '100%'
            }}
            allowClear
            value={value}
            onChange={handleSelectChange}
            placeholder={"Tìm kiếm"}
        >
            {
                mapSelectData()
            }
        </Select>
    );
};

export default SelectParentTask;