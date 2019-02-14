const initialState = {
    test: 'test value',
    messages: ['']
};

const testReducer = (state = initialState, action) => {
    console.log(action);
    switch (action.type) {
        case 'LOAD_MESSAGES':
            return Object.assign({}, state, {
                messages: action.messages,
            });
            break;
        case 'IMPORT_MESSAGE':
            const newMessages = state.messages.slice(0);
            newMessages.push(action.message);
            return Object.assign({}, state, {
                messages: newMessages,
            })
            break;
        default:
            return state;
    }
};

export default testReducer;