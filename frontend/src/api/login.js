import axios from "axios";
import { custom_axios } from "../custom/axios";

const baseURL = "http://localhost:8080"

export async function login(data) {

    const config = {
        headers: { "Content-Type": "application/json" }
    };

    const response = await axios.post(baseURL + "/jwt/login", data, config)

    return response || []
}

export async function refreshToken(data) {

    const config = {
        headers: {
            "Content-Type": "application/json"
        }
    }

    const response = await axios.post(baseURL + "/refreshToken", data, config)

    return response || []
}

export async function register(data) {
    const config = {
        headers: { "Content-Type": "application/json" }
    };

    const response = await custom_axios.post(baseURL + "/account/register", data, config)

    return response || []
}