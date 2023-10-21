package com.investly.investly.service;

import com.investly.investly.model.*;
import com.investly.investly.model.enums.GameChallengeState;
import com.investly.investly.model.enums.GameState;
import com.investly.investly.model.enums.PlayStateState;
import com.investly.investly.repository.GameRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GameService {
    private final GameChallengeService gameChallengeService;
    private final GameRepository gameRepository;
    private final ArrayList<String> challenges;

    public GameService(GameChallengeService gameChallengeService, GameRepository gameRepository) {
        this.gameChallengeService = gameChallengeService;
        this.gameRepository = gameRepository;
        challenges = new ArrayList<>(Arrays.asList("Gewinn", "Kredit", "Zinsen", "Kapital", "Steuer", "Kursen", "Mieten"));
    }

    public Game createGame(CreateGameDto createGameDto) {
        var challenge = gameChallengeService.getChallenge(createGameDto.getChallengeId());

        if (challenge == null || challenge.getState() != GameChallengeState.ACCEPTED) {
            return null;
        }

        var game = new Game();

        var player1State = new Game.PlayState();
        player1State.setState(PlayStateState.ONGOING);
        var player2State = new Game.PlayState();
        player2State.setState(PlayStateState.ONGOING);

        game.setChallengeWord(challenges.get(0));
        game.setPlayer1(challenge.getChallenger());
        game.setPlayer1State(player1State);
        game.setPlayer2(challenge.getChallenged());
        game.setPlayer2State(player2State);
        game.setState(GameState.PLAYING);

        gameChallengeService.redeemChallenge(challenge);
        challenges.remove(0);

        return gameRepository.save(game);
    }

    public boolean gameExists(String challengeId) {
        return gameRepository.existsById(challengeId);
    }

    public Game getGame(String challengeId) {
        return gameRepository.findById(challengeId).orElse(null);
    }

    public List<Game> listAssociatedGames(User user) {
        return gameRepository.findGameChallengesByPlayer1OrPlayer2(user, user);
    }

    public Game updateGameState(Game game, UpdateGameDto updateGameDto, User user) {
        if (game.getState() != GameState.PLAYING) {
            return null;
        }

        var playState = new Game.PlayState();
        BeanUtils.copyProperties(updateGameDto, playState);
        boolean done = false;

        if (game.getPlayer1().getId().equals(user.getId())) {
            game.setPlayer1State(playState);

            if (game.getPlayer2State().getState() != PlayStateState.ONGOING) {
                done = true;
            }
        } else if (game.getPlayer2().getId().equals(user.getId())) {
            game.setPlayer2State(playState);

            if (game.getPlayer1State().getState() != PlayStateState.ONGOING) {
                done = true;
            }
        } else {
            return null;
        }

        var player1State = game.getPlayer1State();
        var player2State = game.getPlayer2State();

        if (done) {
            game.setState(GameState.SECURITY_CHOOSING);

            if (player1State.getState() == player2State.getState()) {
                switch (player1State.getState()) {
                    case FAILED -> game.setState(GameState.DONE); //stale will lead to skip of security choosing
                    case PARTIAL -> {
                        if (player1State.getCorrectCharacterCount() > player2State.getCorrectCharacterCount()) {
                            game.setWinner(game.getPlayer1());
                        } else if (player1State.getCorrectCharacterCount() < player2State.getCorrectCharacterCount()) {
                            game.setWinner(game.getPlayer2());
                        } else {
                            game.setState(GameState.DONE); //stale will lead to skip of security choosing
                        }
                    }
                    case COMPLETED -> {
                        long player1Diff = ChronoUnit.NANOS.between(player1State.getEndedAt(), player1State.getStartedAt());
                        long player2Diff = ChronoUnit.NANOS.between(player2State.getEndedAt(), player2State.getStartedAt());

                        if (player1Diff > player2Diff) {
                            game.setWinner(game.getPlayer1());
                        } else {
                            game.setWinner(game.getPlayer2());
                        }
                    }
                }
            } else {
                if (player1State.getState().getRank() > player2State.getState().getRank()) {
                    game.setWinner(game.getPlayer1());
                } else {
                    game.setWinner(game.getPlayer2());
                }
            }

        }

        return gameRepository.save(game);
    }
}