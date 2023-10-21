package com.investly.investly.control;

import com.investly.investly.model.CreateSessionDto;
import com.investly.investly.model.Session;
import com.investly.investly.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<Session> createUser(@RequestBody CreateSessionDto createSessionDto) {
        var session = sessionService.createSession(createSessionDto);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(session, HttpStatus.OK);
    }
}