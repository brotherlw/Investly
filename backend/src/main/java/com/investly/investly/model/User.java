package com.investly.investly.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    private String id;
    private String username;
    @JsonIgnore
    private String passwordHash;
}
