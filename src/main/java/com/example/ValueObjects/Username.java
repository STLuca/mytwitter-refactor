package com.example.ValueObjects;

import com.example.Exceptions.ValidationException;
import lombok.Value;

import javax.persistence.Embeddable;

@Embeddable
@Value
public class Username {

    private final String username;

    public Username(String username) {

        if (username.length() < 3) {
            throw new ValidationException("username", username, "Username must be greater than 2 characters");
        }

        if (username.length() > 20) {
            throw new ValidationException("username", username, "Username must be less than 20 characters");
        }


        this.username = username;
    }

}
