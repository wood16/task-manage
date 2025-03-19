import { custom_axios } from "../custom/axios"

const baseURL = "/users"

export async function getAllUser(search) {

    const config = {
        params: {
            search
        }
    }

    const response = await custom_axios.get(baseURL, config)

    return response || []
}

export async function getAllUserRole(search) {

    const config = {
        params: {
            search
        }
    }

    const response = await custom_axios.get(baseURL + "/role/user", config)

    return response || []
}