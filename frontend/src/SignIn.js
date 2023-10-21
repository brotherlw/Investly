import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
//import { useAuth } from './auth/Auth';

import {
    Alert,
    Box,
    Button,
    Center,
    CircularProgress,
    Container,
    FormControl,
    FormLabel,
    Grid,
    Heading,
    Input,
    Stack,
    Text,
} from '@chakra-ui/react';

const SignIn = () => {
    const [credentials, setCredentials] = useState({ username: '', password: '' });
    const [error, setError] = useState(false);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    //const { login } = useAuth();

    const handleUsernameChange = (event) => {
        setCredentials({ ...credentials, username: event.target.value });
    };

    const handlePasswordChange = (event) => {
        setCredentials({ ...credentials, password: event.target.value });
    };

    const handleSubmit = async (event) => {
        setLoading(true);
        setError(false);
        event.preventDefault();

        const isSuccessful = await login(credentials);

        if (!isSuccessful) {
            setError(true);
        }

        setLoading(false);
    };

    return (
        <Center minH="100vh">
            <Container maxW="md" p={8} rounded="lg" boxShadow="lg" bg="white">
                {loading && (
                    <Center>
                        <CircularProgress isIndeterminate color="blue.500" />
                    </Center>
                )}
                <Heading mb={4} textAlign="center">
                    Sign in
                </Heading>
                <form onSubmit={handleSubmit}>
                    <Stack spacing={4}>
                        <FormControl isRequired>
                            <FormLabel>Username</FormLabel>
                            <Input
                                type="text"
                                value={credentials.username}
                                onChange={handleUsernameChange}
                                disabled={loading}
                            />
                        </FormControl>
                        <FormControl isRequired>
                            <FormLabel>Password</FormLabel>
                            <Input
                                type="password"
                                value={credentials.password}
                                onChange={handlePasswordChange}
                                disabled={loading}
                            />
                        </FormControl>
                        <Button
                            type="submit"
                            colorScheme="blue"
                            size="lg"
                            isLoading={loading}
                            loadingText="Signing in"
                        >
                            Sign In
                        </Button>
                    </Stack>
                </form>
                {error && (
                    <Alert status="error" mt={4}>
                        <Text fontSize="sm">DAT LÖPPT NICHT !!!</Text>
                    </Alert>
                )}
                <Grid mt={4} templateColumns="repeat(2, 1fr)" gap={2}>
                    <Button variant="link" onClick={() => alert("sucks to be you")}>
                        Forgot password?
                    </Button>
                    <Button as={ReactLink} to="/signup" variant="link">
                        Don't have an account? Sign Up
                    </Button>
                </Grid>
            </Container>
        </Center>
    );
};

export default SignIn;
