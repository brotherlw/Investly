package com.investly.investly.model;

import com.investly.investly.model.enums.FriendRequestState;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "friend request")
@Data
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn
    private User receiver;

    @ManyToOne
    @JoinColumn
    private User requester;
    private FriendRequestState state;
}