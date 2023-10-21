package com.investly.investly.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.investly.investly.model.CreateSessionDto;
import com.investly.investly.model.Session;
import com.investly.investly.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserService userService;

    public Session createSession(CreateSessionDto createSessionDto) {
        var user = userService.getUser(createSessionDto.getUsername());

        if (user == null) {
            return null;
        }

        if (!BCrypt.verifyer().verify(createSessionDto.getPassword().toCharArray(), user.getPasswordHash()).verified) {
            return null;
        }

        var session = new Session();
        session.setUser(user);

        return sessionRepository.save(session);
    }

    public Session getSession(String sessionId) {
        return sessionRepository.findById(sessionId).orElse(null);
    }

    public boolean sessionExists(String sessionId) {
        return sessionRepository.existsById(sessionId);
    }
}