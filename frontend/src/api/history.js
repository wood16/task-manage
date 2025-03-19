import { custom_axios } from "../custom/axios"

const baseURL = "/histories"

export async function getHistoryObject(type, objectId) {

    const config = {
        params: {
            type,
            objectId
        }
    }

    const response = await custom_axios.get(baseURL + "/object", config)

    return response || []
}