import { TOKEN_CHECK } from  '../types/EventTypes';

export default function(state = null, event) {
    switch (event.type) {
        case TOKEN_CHECK:
            return event.payload;
        default:
            return state;
    };
}