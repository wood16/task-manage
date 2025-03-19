const initialState = {
    // Your initial state
    loading: false,
    search: null,
    openTask: {},
};

const layoutReducer = (state = initialState, action) => {
    console.log("LAM redux", action)

    switch (action.type) {
        case "LOAD_DATA":
            return {
                ...state,
                loading: !state.loading
            }
        case "CHANGE_SEARCH_DATA":
            return {
                ...state,
                search: action.payload
            }
        case "ADD_TASK":
            return {
                ...state,
                openTask: action.payload
            }
        case "VIEW_TASK":
            return {
                ...state,
                viewTask: action.payload
            }
        default:
            return state;
    }
}

export default layoutReducer;