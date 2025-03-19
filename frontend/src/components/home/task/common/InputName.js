import { Button, Input, Space } from 'antd';
import React, { useState } from 'react';

function InputName({ initValue, onChange }) {

    const [value, setValue] = useState(initValue);

    return (
        <div>
            <Space.Compact
                style={{
                    width: '100%',
                }}
            >
                <Input
                    defaultValue={initValue}
                    onChange={(e) => setValue(e.target.value)}
                    onPressEnter={(e) => onChange(e.target.value)}
                />
                <Button type="primary" onClick={() => onChange(value)}>Lưu</Button>
            </Space.Compact>
        </div>
    );
}

export default InputName;