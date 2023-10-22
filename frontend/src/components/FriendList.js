import {useEffect, useState} from "react";
import {IconButton, Table, TableContainer, Tbody, Td, Th, Thead, Tr} from "@chakra-ui/react";
import {BellIcon, CheckIcon, NotAllowedIcon, TimeIcon} from "@chakra-ui/icons";
import {useAuth} from "../auth/Auth";

const FriendList = () => {
    const [friends, setFriends] = useState([])
    const [error, setError] = useState(false)
    const {session} = useAuth();

    const populateFriendsList = async () => {
        setError(false)

        try {
            let response = await fetch(`${process.env.REACT_APP_BACKEND_BASE}/friend-connections`, {
                method: "GET",
                headers: {
                    "Authorization": session.id
                }
            });

            if (response.ok) {
                const json = await response.json();

                const userId = session.user.id;
                let transformed = json.map(element => {
                    if (element.party1.id === userId) {
                        return {
                            id: element.id,
                            friend: element.party2
                        }
                    } else {
                        return {
                            id: element.id,
                            friend: element.party1
                        }
                    }
                })

                response = await fetch(`${process.env.REACT_APP_BACKEND_BASE}/game-challenges`, {
                    method: "GET",
                    headers: {
                        "Authorization": session.id
                    }
                });

                if (response.ok) {
                    const json = await response.json();

                    json.forEach(request => {
                        if (request.state === 'PENDING') {
                            if (request.challenged.id === userId) {
                                transformed = transformed.map(elem => {
                                    if (elem.friend.id === request.challenger.id) {
                                        elem.state = 'ACCEPTABLE'
                                        elem.challengeId = request.id
                                    }

                                    return elem
                                })
                            } else {
                                transformed = transformed.map(elem => {
                                    if (elem.friend.id === request.challenged.id) {
                                        elem.state = 'WAITING'
                                        elem.challengeId = request.id
                                    }

                                    return elem
                                })
                            }
                        } else if (request.state === 'ACCEPTED') {
                            if (request.challenged.id === userId) {
                                transformed.find(elem => (elem.friend.id === request.challenger.id)).state = 'DENIED'
                            } else {
                                transformed.find(elem => (elem.friend.id === request.challenged.id)).state = 'DENIED'
                            }
                        } else if (request.state === 'DECLINED') {
                            if (request.challenged.id === userId) {
                                transformed.find(elem => (elem.friend.id === request.challenger.id)).state = 'DENIED'
                            } else {
                                transformed.find(elem => (elem.friend.id === request.challenged.id)).state = 'DENIED'
                            }
                        } else if (request.state === 'REDEEMED') {
                            if (request.challenged.id === userId) {
                                transformed.find(elem => (elem.friend.id === request.challenger.id)).state = 'DENIED'
                            } else {
                                transformed.find(elem => (elem.friend.id === request.challenged.id)).state = 'DENIED'
                            }
                        }
                    })

                    transformed = transformed.map(entry => {
                        if (entry.state === undefined) {
                            entry.state = 'CHALLENGABLE'
                        }

                        return entry
                    })
                } else {
                    setError(true)
                }

                setFriends(transformed)
            } else {
                setError(true)
            }
        } catch (e) {
            setError(true)
        }
    }

    useEffect(() => {
        const interval = setInterval(populateFriendsList, 1000)

        return () => {
            clearInterval(interval);
        }
    }, []);

    const handleClick = async (connection) => {
        if (connection.state === 'CHALLENGABLE') {
            const response = await fetch(`${process.env.REACT_APP_BACKEND_BASE}/game-challenges`, {
                method: "POST",
                headers: {
                    'Content-Type': "application/json",
                    "Authorization": session.id
                },
                body: JSON.stringify({
                    challengedId: connection.friend.id
                })
            });
        } else if (connection.state === 'ACCEPTABLE') {
            let response = await fetch(`${process.env.REACT_APP_BACKEND_BASE}/game-challenges/${connection.challengeId}`, {
                method: "PATCH",
                headers: {
                    'Content-Type': "application/json",
                    "Authorization": session.id
                },
                body: JSON.stringify({
                    state: 'ACCEPTED'
                })
            });

            await response.json()

            response = await fetch(`${process.env.REACT_APP_BACKEND_BASE}/games`, {
                method: "POST",
                headers: {
                    'Content-Type': "application/json",
                    "Authorization": session.id
                },
                body: JSON.stringify({
                    "challengeId": connection.challengeId
                })
            });

            await response.json()
        }
    }

    return (
        <>
            <TableContainer>
                <Table variant='simple'>
                    <Thead>
                        <Tr>
                            <Th>Username</Th>
                            <Th>Aktionen</Th>
                        </Tr>
                    </Thead>
                    <Tbody>
                        {friends.map(friendConnection => (
                            <Tr>
                                <Td>{friendConnection.friend.username}</Td>
                                <Td><IconButton
                                    key={friendConnection.friend.id}
                                    isRound={true}
                                    variant='solid'
                                    colorScheme='green'
                                    aria-label='Accept'
                                    fontSize='20px'
                                    icon={friendConnection.state === 'ACCEPTABLE' ? <CheckIcon/> : (friendConnection.state === 'WAITING' ?
                                        <TimeIcon/> : ((friendConnection.state === 'DENIED' ? <NotAllowedIcon/> : <BellIcon/>)))}
                                    isDisabled={friendConnection.state === 'WAITING' || friendConnection.state === 'DENIED'}
                                    onClick={() => handleClick(friendConnection)}
                                /></Td>
                            </Tr>
                        ))}
                    </Tbody>
                </Table>
            </TableContainer>
        </>
    )
}

export default FriendList