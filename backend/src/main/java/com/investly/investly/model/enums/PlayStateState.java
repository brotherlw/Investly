package com.investly.investly.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PlayStateState {
    COMPLETED(3),
    PARTIAL(2),
    FAILED(1),
    ONGOING(0);

    private final int rank;
}
