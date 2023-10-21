package com.investly.investly.service;

import com.investly.investly.model.CreateGameChallengeDto;
import com.investly.investly.model.GameChallenge;
import com.investly.investly.model.UpdateGameChallengeDto;
import com.investly.investly.model.User;
import com.investly.investly.model.enums.GameChallengeState;
import com.investly.investly.repository.GameChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameChallengeService {
    private final GameChallengeRepository gameChallengeRepository;

    public GameChallenge createChallenge(CreateGameChallengeDto createGameChallengeDto, User challenger) {
        var gameChallenge = new GameChallenge();

        var challenged = new User();
        challenged.setId(createGameChallengeDto.getChallengedId());
        gameChallenge.setChallenged(challenged);
        gameChallenge.setChallenger(challenger);
        gameChallenge.setState(GameChallengeState.PENDING);

        //TODO make sure challenger and challenged are friends
        //TODO make sure both challenger and challenged have daily challenges left (not played 3)
        //TODO automatically reject all other pending challenges when challenger or challenged played 3rd game of the day

        return gameChallengeRepository.save(gameChallenge);
    }

    public GameChallenge getChallenge(String challengeId) {
        return gameChallengeRepository.findById(challengeId).orElse(null);
    }

    public List<GameChallenge> listAssociatedChallenges(User user) {
        return gameChallengeRepository.findGameChallengesByChallengedOrChallenger(user, user);
    }

    public GameChallenge answerChallenge(String id, UpdateGameChallengeDto gameChallenge, User user) {
        var savedChallenge = getChallenge(id);

        if (savedChallenge.getState() != GameChallengeState.PENDING) {
            return null;
        }

        savedChallenge.setState(gameChallenge.getState());

        if (!user.getId().equals(savedChallenge.getChallenged().getId())) {
            return null;
        }

        return gameChallengeRepository.save(savedChallenge);
    }

    public void redeemChallenge(GameChallenge gameChallenge) {
        gameChallenge.setState(GameChallengeState.REDEEMED);

        gameChallengeRepository.save(gameChallenge);
    }
}