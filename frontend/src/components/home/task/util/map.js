import { CheckCircleOutlined, ClockCircleOutlined, CloseCircleOutlined, DownCircleOutlined, MinusCircleOutlined, SyncOutlined, UpCircleOutlined } from "@ant-design/icons"
import { Progress, Tag } from "antd"
import dayjs from "dayjs"


export function mapStatus(status) {
    switch (status) {
        case "pending":
            return <Tag bordered={false} icon={<ClockCircleOutlined />} color="warning">Chờ xử lý</Tag>
        case "processing":
            return <Tag icon={<SyncOutlined spin />} color="processing">Đang xử lý</Tag>
        case "complete":
            return <Tag icon={<CheckCircleOutlined />} color="success">Hoàn thành</Tag>
        case "cancel":
            return <Tag icon={<CloseCircleOutlined />} color="error">Hủy</Tag>
        case "pause":
            return <Tag icon={<MinusCircleOutlined />} color="default">Tạm dừng</Tag>
        default:
            return <Tag icon={<MinusCircleOutlined />} color="default">Mặc định</Tag>
    }
}

export function mapStatusSelect(status) {
    switch (status) {
        case "pending":
            return <span style={{ color: "#faad14" }}><ClockCircleOutlined /> Chờ xử lý</span>
        case "processing":
            return <span style={{ color: "#1677ff" }}><SyncOutlined spin /> Đang xử lý</span>
        case "complete":
            return <span style={{ color: "#52c41a" }}><CheckCircleOutlined /> Hoàn thành</span>
        case "cancel":
            return <span style={{ color: "#ff4d4f" }}><CloseCircleOutlined /> Hủy</span>
        case "pause":
            return <span ><MinusCircleOutlined /> Tạm dừng</span>
        default:
            return <span ><MinusCircleOutlined /> Mặc định</span>
    }
}

export function mapDate(date, format) {

    return dayjs(date).format(format);
}

export function mapPriority(priority) {
    switch (priority) {
        case "high":
            return <><UpCircleOutlined /> Cao</>
        case "normal":
            return <><MinusCircleOutlined /> Trung bình</>
        case "low":
            return <><DownCircleOutlined /> Thấp</>
        default:
            return <><MinusCircleOutlined /> Trung bình</>
    }
}

export function mapProgress(progress) {

    return <Progress
        percent={progress}
        percentPosition={{
            align: 'start',
            type: 'inner',
        }}
        size={[200, 15]}
    />
}

export function mapPrioritySelect(priority) {
    switch (priority) {
        case "high":
            return <span style={{ color: "#ff4d4f" }}><UpCircleOutlined /> Cao</span>
        case "normal":
            return <span style={{ color: "#faad14" }}><MinusCircleOutlined /> Trung bình</span>
        case "low":
            return <span style={{ color: "#52c41a" }}><DownCircleOutlined /> Thấp</span>
        default:
            return <span style={{ color: "#faad14" }}><MinusCircleOutlined /> Trung bình</span>
    }
}