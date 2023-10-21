package com.investly.investly.model;

import com.investly.investly.model.enums.GameChallengeState;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "game-challenges")
@Data
public class GameChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "challengedId")
    private User challenged;

    @ManyToOne
    @JoinColumn(name = "challengerId")
    private User challenger;
    private GameChallengeState state;
}
