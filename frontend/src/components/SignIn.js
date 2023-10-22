import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {useAuth} from '../auth/Auth';
import {
    Alert,
    AlertDescription,
    AlertIcon,
    AlertTitle,
    Button,
    Card,
    CardBody,
    CardHeader,
    Heading,
    Input
} from "@chakra-ui/react";

export const SignIn = () => {
    const [credentials, setCredentials] = useState({username: '', password: ''});
    const [error, setError] = useState(false);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const {session, login} = useAuth();

    if (session) {
        navigate("/");
    }

    const handleUsernameChange = (event) => {
        setCredentials({...credentials, username: event.target.value});
    };

    const handlePasswordChange = (event) => {
        setCredentials({...credentials, password: event.target.value});
    };

    const handleSubmit = async (event) => {
        setLoading(true)
        setError(false)
        event.preventDefault()

        const isSuccessful = await login(credentials);

        if (!isSuccessful) {
            setError(true)
        }

        setLoading(false)
    };

    return (
        <>
            <Card maxW='sm'>
                <CardHeader>
                    <Heading size='md'>Login</Heading>
                </CardHeader>

                <CardBody>
                    <form onSubmit={handleSubmit}>
                        <Input
                            placeholder='Username'
                            disabled={loading}
                            value={credentials.username}
                            onChange={handleUsernameChange}
                        />
                        <Input
                            placeholder='Password'
                            type="password"
                            disabled={loading}
                            value={credentials.password}
                            onChange={handlePasswordChange}
                        />
                        <Button
                            type="submit"
                            variant="solid"
                            colorSchema="cyan"
                            isLoading={loading}
                        >
                            Sign In
                        </Button>
                        {error && <Alert status='error'>
                            <AlertIcon />
                            <AlertTitle>Error logging in!</AlertTitle>
                            <AlertDescription>Credentials not valid or error communicating.</AlertDescription>
                        </Alert>}
                    </form>
                </CardBody>
            </Card>

            {/*<Container component="main" maxWidth="xs">
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Typography component="h1" variant="h5">
                        Sign in
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{mt: 1}}>
                        <TextField
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            label="Username"
                            autoFocus
                            disabled={loading}
                            value={credentials.username}
                            onChange={handleUsernameChange}
                        />
                        <TextField
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            label="Password"
                            type="password"
                            disabled={loading}
                            value={credentials.password}
                            onChange={handlePasswordChange}
                        />
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{mt: 3, mb: 2}}
                        >
                            Sign In
                        </Button>
                        {error && <Alert severity="error">DAT LÖPPT NICHT !!!</Alert>}
                        <Grid container>
                            <Grid item xs>
                                <Button onClick={() => alert("sucks to be you")}>
                                    Forgot password?
                                </Button>
                            </Grid>
                            <Grid item>
                                <Button component={Link} to="/signup">
                                    Don't have an account? Sign Up
                                </Button>
                            </Grid>
                        </Grid>
                    </Box>
                </Box>
            </Container>*/}
        </>
    );
};

export default SignIn;