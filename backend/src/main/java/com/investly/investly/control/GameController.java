package com.investly.investly.control;

import com.investly.investly.model.CreateGameDto;
import com.investly.investly.model.Game;
import com.investly.investly.model.UpdateGameDto;
import com.investly.investly.service.GameService;
import com.investly.investly.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody CreateGameDto createGameDto,
                                           @RequestHeader String authorization) {
        var session = sessionService.getSession(authorization);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var game = gameService.createGame(createGameDto);

        if (game == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Game>> listGame(@RequestHeader String authorization) {
        var session = sessionService.getSession(authorization);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(gameService.listAssociatedGames(session.getUser()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Game> getGame(@RequestHeader String authorization, @PathVariable String id) {
        var session = sessionService.getSession(authorization);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(gameService.getGame(id), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Game> updateGame(@RequestHeader String authorization, @PathVariable String id,
                                                         @RequestBody UpdateGameDto updateGameDto) {
        var session = sessionService.getSession(authorization);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (updateGameDto.getState() == null || updateGameDto.getEndedAt() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var game = gameService.getGame(id);

        var updatedGame = gameService.updateGameState(game, updateGameDto, session.getUser());

        if (updatedGame == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(updatedGame, HttpStatus.OK);
    }
}