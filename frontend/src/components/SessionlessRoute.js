import {Navigate} from "react-router-dom";
import {useAuth} from "../auth/Auth";

export const SessionlessRoute = ({children}) => {
    const {session} = useAuth();

    if (session) {
        // user is authenticated
        return <Navigate to="/" replace={true}/>
    }

    return children;
};