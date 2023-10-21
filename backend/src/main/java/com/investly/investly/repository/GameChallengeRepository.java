package com.investly.investly.repository;

import com.investly.investly.model.GameChallenge;
import com.investly.investly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameChallengeRepository extends JpaRepository<GameChallenge, String> {
    List<GameChallenge> findGameChallengesByChallengedOrChallenger(User challenged, User challenger);
}