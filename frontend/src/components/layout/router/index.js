import React from 'react';
import { Route, Routes } from "react-router-dom";
import HomeTask from "../../home/HomeTask";
import AllTask from '../../home/task/all/AllTask';
import AllBoard from '../../home/board/all/AllBoard';

function MainRouter(props) {
    return (
        <Routes>
            <Route index element={<HomeTask />} />
            <Route path={"/home"} element={<HomeTask />} />
            <Route path={"/tasks"} >
                <Route path={"all"} element={<AllTask type={"all"} />} />
                <Route path={"myTask"} element={<AllTask type={"assignee"} />} />
            </Route>
            <Route path={"/board"} >
                <Route path={"all"} element={<AllBoard type={"all"} />} />
                <Route path={"myTask"} element={<AllBoard type={"assignee"} />} />
            </Route>
        </Routes>
    );
}

export default MainRouter;