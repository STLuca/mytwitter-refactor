package com.example.util;

import lombok.Value;

@Value
public class Mention {

    private final String username;

    public String getLink() {
        return ControllerUrlBuilder.userUrl(username);
    }
}
