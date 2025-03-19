import { custom_axios } from "../custom/axios/index";


const baseURL = "/tasks"

export async function getAllTask(filter, page, pageSize, search, sortBy, sortOrder) {

    const config = {
        params: {
            filter,
            page,
            pageSize,
            search,
            sortBy,
            sortOrder
        }
    }

    const response = await custom_axios.get(baseURL, config)

    return response || []
}

export async function postTask(value) {

    const response = await custom_axios.post(baseURL, value)

    return response || []
}

export async function putTask(id, value) {

    const response = await custom_axios.put(baseURL + `/${id}`, value)


    return response || []
}

export async function getTask(id) {

    const response = await custom_axios.get(baseURL + `/${id}`,)

    return response || []
}

export async function deleteTask(id) {

    const response = await custom_axios.delete(baseURL + `/${id}`,)

    return response || []
}

export async function patchTask(id, value) {

    const response = await custom_axios.patch(baseURL + `/${id}`, value)

    return response || []
}