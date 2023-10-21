package com.investly.investly.control;

import com.investly.investly.model.FriendConnection;
import com.investly.investly.service.FriendConnectionService;
import com.investly.investly.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("friend-connections")
@RequiredArgsConstructor
public class FriendConnectionController {
    private final FriendConnectionService friendConnectionService;
    private final SessionService sessionService;

    @GetMapping
    public ResponseEntity<List<FriendConnection>> listRequests(@RequestHeader String authorization) {
        var session = sessionService.getSession(authorization);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(friendConnectionService.listFriendConnections(session.getUser()), HttpStatus.OK);
    }
}