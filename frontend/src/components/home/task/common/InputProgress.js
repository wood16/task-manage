import { AutoComplete, Button, Input, Space } from 'antd';
import React, { useState } from 'react';

const InputProgress = ({ initValue, onChange }) => {

    const [value, setValue] = useState(initValue)

    const generateOptions = (min, max, step) => {

        let array = []

        for (let i = min; i <= max; i += step) {
            array.push({
                value: i
            })
        }

        return array;
    }

    const onChangeValue = (value) => {
        setValue(value)
    }

    const onSubmitValue = (value) => {
        onChange(value)
    }

    return (
        <AutoComplete
            value={value}
            options={generateOptions(0, 100, 10)}
            style={{
                width: 200,
            }}
            onChange={onChangeValue}
            placeholder="Nhập tiến độ"
            onBlur={() => { onSubmitValue(value) }}
            onSelect={(value) => { onSubmitValue(value) }}
        >

        </AutoComplete>
    );
};

export default InputProgress;