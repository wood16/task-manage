import axios from "axios";
import Cookies from "js-cookie";
import { jwtDecode } from "jwt-decode";

const baseURL = "http://localhost:8080"

export const custom_axios = axios.create({
    baseURL: baseURL,
    headers: {
        "Content-Type": "application/json",
    }
});

export const getAccessToken = () => {
    return Cookies.get('token');
}

export const getRefreshToken = () => {
    return Cookies.get('refreshToken');
}

export const updateAccessToken = (value) => {
    return Cookies.set('token', value, { expires: 1 / 24 });
}

export const removeAccessToken = () => {
    Cookies.remove('token')
}

export const getUserInfo = () => {
    return getAccessToken() ? jwtDecode(getAccessToken()) : undefined
}