import { Select } from 'antd';
import React, { useEffect, useState } from 'react';
import { getAllUserRole } from '../../../../api/user';

const Option = Select.Option

const SelectAssignee = ({ value, onChange }) => {

    const [dataAssignee, setDataAssignee] = useState([]);
    let timeout;

    useEffect(() => {
        getAllUserRole(null).then(res => {
            setDataAssignee(res?.data?.result)
        })
    }, [])

    const mapAssigneeData = () => {
        return dataAssignee?.map(item => {
            return (
                <Option value={item?.id} key={item?.id}>
                    {item?.username}
                </Option>
            )
        })
    }

    const handleSelectChange = (selectedValue) => {
        onChange(selectedValue)
    }

    const handleSearch = (searchValue) => {
        if (timeout) {
            clearTimeout(timeout)
            timeout = null
        }

        timeout = setTimeout(() => {
            getAllUserRole(searchValue).then(res => {
                setDataAssignee(res?.data?.result)
            })
        }, 300)
    }

    // set filterOption={false} when search 

    return (
        <Select
            style={{
                width: '100%'
            }}
            allowClear
            showSearch
            filterOption={false}
            value={value}
            onChange={handleSelectChange}
            onSearch={handleSearch}
            placeholder="Tìm kiếm"
        >
            {
                mapAssigneeData()
            }
        </Select>

    );
};

export default SelectAssignee;