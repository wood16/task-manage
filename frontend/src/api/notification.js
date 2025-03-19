import { custom_axios } from "../custom/axios"

const baseURL = "/notifications"

export async function getNotificationOfUser() {

    const response = custom_axios.get(baseURL + "/user")

    return response || []
}

export async function patchNotification(id, object) {

    const response = custom_axios.patch(baseURL + `/${id}`, object)

    return response || []
}