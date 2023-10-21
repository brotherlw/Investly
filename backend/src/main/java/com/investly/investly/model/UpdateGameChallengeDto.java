package com.investly.investly.model;

import com.investly.investly.model.enums.GameChallengeState;
import lombok.Data;

@Data
public class UpdateGameChallengeDto {
    private GameChallengeState state;
}