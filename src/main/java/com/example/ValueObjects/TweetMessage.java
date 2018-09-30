package com.example.ValueObjects;

import com.example.Exceptions.ValidationException;
import lombok.Value;

import javax.persistence.Embeddable;

@Embeddable
@Value
public class TweetMessage {

    private final String message;

    public TweetMessage(String message) {

        if (message.length() == 0) {
            throw new ValidationException("message", message, "Message cannot be empty.");
        }

        if (message.length() > 255) {
            throw new ValidationException("message", message, "Message must be under 256 characters");
        }

        this.message = message;
    }
}
