import {Container, Heading, Tab, TabList, TabPanel, TabPanels, Tabs} from "@chakra-ui/react";
import FriendList from "./FriendList";

const InvestlyHome = () => {
    return (
        <>
            <Container paddingTop="25px">
                <Tabs variant='soft-rounded' colorScheme='cyan'>
                    <TabList>
                        <Tab>Dashboard</Tab>
                        <Tab>Freunde</Tab>
                        <Tab>Aktien</Tab>
                    </TabList>
                    <TabPanels>
                        <TabPanel>
                            <Heading>Dashboard</Heading>
                            <p>Coming soon</p>
                        </TabPanel>
                        <TabPanel>
                            <Heading>Freunde</Heading>
                            <FriendList/>
                        </TabPanel>
                        <TabPanel>
                            <Heading>Aktien</Heading>
                            <p>Coming soon</p>
                        </TabPanel>
                    </TabPanels>
                </Tabs>
            </Container>
        </>
    )
}

export default InvestlyHome