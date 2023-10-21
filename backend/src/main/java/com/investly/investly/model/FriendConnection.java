package com.investly.investly.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "friend-connections")
@Data
public class FriendConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn
    private User party1;

    @ManyToOne
    @JoinColumn
    private User party2;
}
