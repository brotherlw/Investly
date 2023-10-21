package com.investly.investly.repository;

import com.investly.investly.model.Game;
import com.investly.investly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, String> {
    List<Game> findGameChallengesByPlayer1OrPlayer2(User player1, User player2);
}