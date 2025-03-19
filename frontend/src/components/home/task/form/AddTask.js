import React from 'react';
import { useDispatch, useSelector } from "react-redux";
import { onChangeAddTask } from "../../../../redux/actions/layout";
import TaskForm from './TaskForm';

function AddTask(props) {

    const dispatch = useDispatch();

    const { openTask } = useSelector(state => state.layoutReducer)

    return (
        <div>
            <TaskForm open={openTask} handleClose={() => dispatch(onChangeAddTask({
                key: undefined
            }))} />
        </div>

    );
}

export default AddTask;