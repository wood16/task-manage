import React, {useEffect} from 'react';
import {Route, Routes, useNavigate} from "react-router-dom";
import Main from "../layout/Main";
import LoginForm from "../login/LoginForm";
import Cookies from "js-cookie";

function AppRouter(props) {

    const navigate = useNavigate();

    const accessToken = Cookies.get('token')

    useEffect(() => {

        if (accessToken === undefined || accessToken === "") {
            navigate("/login")
        }

    }, [accessToken])

    return (
        <Routes>
            <Route path={"/*"} element={<Main/>}/>
            <Route path={"/login"} element={<LoginForm/>}/>
        </Routes>
    );
}

export default AppRouter;