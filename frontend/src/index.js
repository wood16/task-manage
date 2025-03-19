import React from 'react';
import ReactDOM from 'react-dom/client';
import {Provider} from "react-redux";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {createStore} from "redux";
import './index.css';
import reportWebVitals from './reportWebVitals';
import App from "./components/App";
import {allReducers} from "./redux/reducers";
import setupInterceptors from './custom/axios/setupInterceptors';
import "./index.scss";


const root = ReactDOM.createRoot(document.getElementById('root'));
const store = createStore(allReducers);

root.render(
    // <React.StrictMode>
        <Provider store={store}>
            <BrowserRouter>
                <Routes>
                    <Route path="/*" element={<App/>}/>
                </Routes>
            </BrowserRouter>
        </Provider>
    // </React.StrictMode>
);

setupInterceptors()

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
