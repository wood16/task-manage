import { Select } from "antd";
import React from 'react';
import { mapPrioritySelect } from '../util/map';

const Option = Select.Option

const SelectPriority = ({ value, onChange }) => {

    const handleSelectChange = (selectedValue) => {
        onChange(selectedValue);
    };

    const data = [
        {
            value: "high",
            label: "Cao"
        },
        {
            value: "normal",
            label: "Trung bình"
        },
        {
            value: "low",
            label: "Thấp"
        }

    ]

    const dataOption = (data) => {
        return data.map(item => {
            return (
                <Option value={item.value} key={item.value}>
                    {mapPrioritySelect(item.value)}
                </Option>)
        })
    }

    return (
        <Select
            value={value}
            onChange={handleSelectChange}
        // options={data}
        >
            {dataOption(data)}
        </Select>
    );

};

export default SelectPriority;