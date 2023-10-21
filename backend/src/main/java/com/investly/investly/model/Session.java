package com.investly.investly.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "sessions")
@Data
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
}
