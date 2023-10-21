package com.investly.investly.control;

import com.investly.investly.model.CreateGameChallengeDto;
import com.investly.investly.model.GameChallenge;
import com.investly.investly.model.enums.GameChallengeState;
import com.investly.investly.service.GameChallengeService;
import com.investly.investly.service.SessionService;
import com.investly.investly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("game-challenges")
@RequiredArgsConstructor
public class GameChallengeController {
    private final GameChallengeService gameChallengeService;
    private final SessionService sessionService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<GameChallenge> createChallenge(@RequestBody CreateGameChallengeDto createChallengeDto,
                                                         @RequestHeader String authorization) {
        var session = sessionService.getSession(authorization);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (userService.userExists(createChallengeDto.getChallengedId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var user = session.getUser();

        if (createChallengeDto.getChallengedId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(gameChallengeService.createChallenge(createChallengeDto, user), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GameChallenge>> listChallenges(@RequestHeader String authorization) {
        var session = sessionService.getSession(authorization);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(gameChallengeService.listAssociatedChallenges(session.getUser()), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<GameChallenge> answerChallenge(@RequestHeader String authorization, @PathVariable String id,
                                                         @RequestBody GameChallenge gameChallenge) {
        var session = sessionService.getSession(authorization);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (gameChallenge.getState() == null || gameChallenge.getState() == GameChallengeState.PENDING) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        gameChallenge.setId(id);

        var storedChallenge = gameChallengeService.answerChallenge(gameChallenge, session.getUser());

        if (storedChallenge == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(storedChallenge, HttpStatus.OK);
    }
}