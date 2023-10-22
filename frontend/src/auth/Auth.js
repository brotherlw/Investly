import { createContext, useContext, useMemo } from "react";
import { useNavigate } from "react-router-dom";
import { useLocalStorage } from "./UseLocalStorage";
const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [session, setSession] = useLocalStorage("session", null);
    const navigate = useNavigate();

    // call this function when you want to authenticate the user
    const login = async (data) => {
        try {
            let response = await fetch(`${process.env.REACT_APP_BACKEND_BASE}/sessions`, {
                method: "POST",
                headers: {
                    'Content-Type': "application/json"
                },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                setSession(await response.json());
                navigate("/");
            } else {
                return false
            }
        } catch (e) {
            return false
        }
    };

    // call this function to sign out logged in user
    const logout = () => {
        setSession(null);
        navigate("/login", { replace: true });
    };

    const value = useMemo(
        () => ({
            session,
            login,
            logout
        }),
        [session]
    );
    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
    return useContext(AuthContext);
};