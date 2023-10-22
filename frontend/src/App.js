import {ChakraProvider} from "@chakra-ui/react";
import FooterNav from "./components/FooterNav";
import {AuthProvider} from "./auth/Auth";
import {Route, Routes} from "react-router-dom";
import {SessionlessRoute} from "./components/SessionlessRoute";
import {ProtectedRoute} from "./components/ProtectedRoute";
import InvestlyHome from "./components/InvestlyHome";
import SignIn from "./components/SignIn";

function App() {
    return (
        <ChakraProvider>
            <AuthProvider>
                <Routes>
                    <Route path="/" element={<ProtectedRoute><InvestlyHome/></ProtectedRoute>}/>
                    <Route path="/login" element={<SessionlessRoute><SignIn/></SessionlessRoute>}/>
                </Routes>
                <FooterNav/>
            </AuthProvider>
        </ChakraProvider>
    );
}

export default App;
