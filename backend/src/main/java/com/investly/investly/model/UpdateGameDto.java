package com.investly.investly.model;

import com.investly.investly.model.enums.PlayStateState;
import lombok.Data;

import java.time.Instant;

@Data
public class UpdateGameDto {
    private int correctCharacterCount;
    private Instant startedAt;
    private Instant endedAt;
    private PlayStateState state;
}