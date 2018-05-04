import { TOKEN_CHECK } from  '../types/EventTypes';

let defaultState = {checked: false, isFetching: false}

export default function(state = defaultState, event) {
    switch (event.type) {
        case TOKEN_CHECK:
            return {checked: event.checked, isFetching: event.fetching};
        default:
            return state;
    };
}