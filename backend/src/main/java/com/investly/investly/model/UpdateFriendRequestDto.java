package com.investly.investly.model;

import com.investly.investly.model.enums.FriendRequestState;
import lombok.Data;

@Data
public class UpdateFriendRequestDto {
    private FriendRequestState state;
}