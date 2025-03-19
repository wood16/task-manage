import { Select } from 'antd';
import React, { useState } from 'react';
import { mapStatusSelect } from '../util/map';
import { dataStatus } from '../constant/status';

const Option = Select.Option

const SelectStatus = ({ initValue, onChange }) => {

    const [value, setValue] = useState(initValue)

    const generateOptions = (data) => {
        return data?.map(item =>
            <Option value={item.value} key={item.value}>{mapStatusSelect(item.value)}</Option>
        )
    }

    const handleSelectChange = (selectedValue) => {
        setValue(selectedValue)
        onChange(selectedValue)
    }

    return (
        <Select
            value={value}
            onChange={handleSelectChange}
        >
            {generateOptions(dataStatus)}
        </Select>
    );
};

export default SelectStatus;