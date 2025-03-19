export const reloadData = () => {
    return {
        type: "LOAD_DATA",
    }
}

export const onChangeSearchData = (data) => {
    return {
        type: "CHANGE_SEARCH_DATA",
        payload: data
    }
}

export const onChangeAddTask = (data) => {
    return {
        type: "ADD_TASK",
        payload: data
    }
}

export const onChangeViewTask = (data) => {

    return {
        type: "VIEW_TASK",
        payload: data
    }
}