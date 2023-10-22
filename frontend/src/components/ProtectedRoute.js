import {Navigate} from "react-router-dom";
import {useAuth} from "../auth/Auth";

export const ProtectedRoute = ({children}) => {
    const {session} = useAuth();

    if (!session) {
        // user is not authenticated
        return <Navigate to="/login" replace={true}/>;
    }

    return children;
};