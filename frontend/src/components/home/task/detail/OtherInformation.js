import { Collapse } from 'antd';
import React from 'react';

function OtherInformation(props) {

    const items = [
        {
            key: '1',
            label: 'Thông tin khác',
            children: <p>{"LAM"}</p>,
        },
    ];


    return (
        <div>
            <Collapse ghost items={items} defaultActiveKey={['1']} />
        </div>
    );
}

export default OtherInformation;