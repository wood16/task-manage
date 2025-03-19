import { custom_axios as axiosInstance, getAccessToken, getRefreshToken, removeAccessToken, updateAccessToken } from ".";
import { refreshToken } from "../../api/login";

let refreshingFunc

const setupInterceptors = () => {

    axiosInstance.interceptors.request.use(
        (config) => {

            config.headers["Authorization"] = 'Bearer ' + getAccessToken()

            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    )

    axiosInstance.interceptors.response.use(
        (response) => {

            return response;
        },
        async (error) => {
            const data = error?.response?.data;
            const status = error?.response?.status;
            const originalConfig = error.config;

            if (status === 401) {
                if (data?.message === "Token expiration") {

                    try {
                        refreshingFunc = refreshToken({
                            token: getRefreshToken()
                        })

                        const responseRefresh = await refreshingFunc;
                        const { token } = responseRefresh?.data
                        updateAccessToken(token)

                        return axiosInstance(originalConfig);
                    } catch (_error) {
                        // refreshToken expired 
                        removeAccessToken();
                        window.location.href = `http://localhost:3000/login`

                        return Promise.reject(_error);
                    } finally {

                    }
                } else if (data?.message === "Invalid format 3 part of token") {

                    removeAccessToken();
                    window.location.href = `http://localhost:3000/login`

                    return Promise.reject(error);
                }
            }

            return Promise.reject(error);
        }
    )
}

export default setupInterceptors;