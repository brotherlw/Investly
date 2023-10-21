package com.investly.investly.model;

import com.investly.investly.model.enums.GameState;
import com.investly.investly.model.enums.PlayStateState;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity(name = "games")
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String challengeWord;

    @ManyToOne
    @JoinColumn
    private User player1;

    @ManyToOne
    @JoinColumn
    private User player2;
    private GameState state;

    @OneToOne(cascade=CascadeType.ALL)
    private PlayState player1State;

    @OneToOne(cascade=CascadeType.ALL)
    private PlayState player2State;

    private Instant startedAt;

    @ManyToOne
    @JoinColumn
    private User winner;

    @Entity(name = "game-playstates")
    @Data
    public static class PlayState {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private int correctCharacterCount;
        private Instant startedAt;
        private Instant endedAt;
        private PlayStateState state;
    }
}