import {
    ChakraProvider,
    ColorModeProvider,
    Flex, Heading,
    IconButton,
    Img,
    Menu,
    MenuButton,
    MenuItem,
    MenuList
} from "@chakra-ui/react";
import {AddIcon, EditIcon, ExternalLinkIcon, HamburgerIcon, RepeatIcon} from "@chakra-ui/icons";


function App() {
    return (
        <ChakraProvider>
            <ColorModeProvider
                options={{initialColorMode: 'dark'}}
            >

            </ColorModeProvider>
            <Flex
                as="nav"
                align="left"
                p="4"
                mt="4">
                <Menu>
                    <MenuButton
                        as={IconButton}
                        aria-label='Options'
                        icon={<HamburgerIcon/>}
                        variant='outline'
                    />
                    <MenuList>
                        <MenuItem icon={<AddIcon/>}>
                            Freunde
                        </MenuItem>
                        <MenuItem icon={<ExternalLinkIcon/>}>
                            Dashboard
                        </MenuItem>
                        <MenuItem icon={<EditIcon/>}>
                            Aktien
                        </MenuItem>
                    </MenuList>
                </Menu>
            </Flex>
            <Flex
                p="4"
                mt="4"
                align="center"
                justify="center">
                <Heading as='h1' size='xl' text-align='center'>
                    INVESTLY
                </Heading>
            </Flex>

            <Flex
                position="fixed"
                bottom="0"
                left="0"
                right="0"
                justify="space-around"
                align="center"
                bg="#02404A"
                p="2"
                boxShadow="0 -4px 6px -1px rgba(0,0,0,0.1)"
            >

                <IconButton icon={<Img src="/img/a.png" boxSize="25px"/>} aria-label={"Sack"}/>
                <IconButton icon={<Img src="/img/b.png" boxSize="25px"/>} aria-label={"Sack"}/>
                <IconButton icon={<Img src="/img/c.png" boxSize="25px"/>} aria-label={"Sack"}/>
                <IconButton icon={<Img src="/img/d.png" boxSize="25px"/>} aria-label={"Sack"}/>
                <IconButton icon={<Img src="/img/e.png" boxSize="25px"/>} aria-label={"Sack"}/>

            </Flex>
        </ChakraProvider>
    );
}

export default App;
