import {Flex, IconButton, Img} from "@chakra-ui/react";

const FooterNav = () => {
    return (
        <>
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
        </>
    )
}

export default FooterNav